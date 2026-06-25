package com.nt.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.service.ICitizenApplicationRegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/CitizenAR-api")
public class CitizenApplicationRegistrationOperationsController {

    @Autowired
    private ICitizenApplicationRegistrationService registrationService;

    @PostMapping("/save")
    public ResponseEntity<String> saveCitizenApplication(
            @Valid
            @RequestBody
            CitizenAppRegistrationInputs inputs) {

        Integer appId =
                registrationService.registerCitizenApplication(inputs);

        String message =
                "Citizen Application is registered with the id :: "
                + appId;

        return new ResponseEntity<>(
                message,
                HttpStatus.CREATED
        );
    }
}