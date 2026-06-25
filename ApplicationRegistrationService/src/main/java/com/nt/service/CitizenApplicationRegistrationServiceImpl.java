package com.nt.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.exceptions.InvalidSSNException;
import com.nt.exceptions.SsaWebApiException;
import com.nt.repository.IApplicationRegistrationRepository;

import reactor.core.publisher.Mono;

@Service
public class CitizenApplicationRegistrationServiceImpl
        implements ICitizenApplicationRegistrationService {

    @Autowired
    private IApplicationRegistrationRepository citizenRepo;

    @Autowired
    private WebClient client;

    @Value("${ar.ssa-web.url}")
    private String endpointUrl;

    @Value("${ar.state}")
    private String targetState;

    @Override
    public Integer registerCitizenApplication(
            CitizenAppRegistrationInputs inputs)
            throws InvalidSSNException {

        // Additional service-level SSN validation
        validateSsn(inputs.getSsn());

        String stateName;

        try {
            stateName = client.get()
                    .uri(endpointUrl, inputs.getSsn())
                    .retrieve()

                    // Handle 4xx responses from SSA Web API
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            response -> response
                                    .bodyToMono(String.class)
                                    .defaultIfEmpty("Invalid SSN.")
                                    .map(InvalidSSNException::new)
                    )

                    // Handle 5xx responses from SSA Web API
                    .onStatus(
                            HttpStatusCode::is5xxServerError,
                            response -> Mono.error(
                                    new SsaWebApiException(
                                            "SSA Web API is currently unavailable. "
                                            + "Please try again later."
                                    )
                            )
                    )

                    .bodyToMono(String.class)
                    .block();

        } catch (InvalidSSNException exception) {
            throw exception;

        } catch (SsaWebApiException exception) {
            throw exception;

        } catch (WebClientRequestException exception) {
            throw new SsaWebApiException(
                    "Unable to connect with SSA Web API. "
                    + "Please check whether SSA API is running."
            );

        } catch (Exception exception) {
            throw new SsaWebApiException(
                    "Unexpected error occurred while calling SSA Web API."
            );
        }

        if (stateName == null || stateName.isBlank()) {
            throw new InvalidSSNException(
                    "Invalid SSN. No state found for the given SSN."
            );
        }

        // Remove accidental leading/trailing spaces from SSA response
        stateName = stateName.trim();

        if (!stateName.equalsIgnoreCase(targetState)) {
            throw new InvalidSSNException(
                    "Citizen does not belong to "
                    + targetState
                    + " state."
            );
        }

        CitizenAppRegistrationEntity entity =
                new CitizenAppRegistrationEntity();

        BeanUtils.copyProperties(inputs, entity);
        entity.setStateName(stateName);

        CitizenAppRegistrationEntity savedEntity =
                citizenRepo.save(entity);

        return savedEntity.getAppId();
    }

    private void validateSsn(Long ssn) {

        if (ssn == null) {
            throw new InvalidSSNException(
                    "SSN is required."
            );
        }

        if (ssn < 100_000_000L ||
                ssn > 999_999_999L) {

            throw new InvalidSSNException(
                    "SSN must contain exactly 9 digits."
            );
        }
    }
}