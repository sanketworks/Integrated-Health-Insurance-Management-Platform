package com.nt.bindings;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CitizenAppRegistrationInputs {

    private Integer appId;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Gender is required.")
    @Pattern(
        regexp = "^[MF]$",
        message = "Gender must be M or F."
    )
    private String gender;

    @NotNull(message = "Phone number is required.")
    private Long phoneNo;

    @NotNull(message = "SSN is required.")
    @Min(
        value = 100_000_000L,
        message = "SSN must contain exactly 9 digits."
    )
    @Max(
        value = 999_999_999L,
        message = "SSN must contain exactly 9 digits."
    )
    private Long ssn;

    @NotNull(message = "Date of birth is required.")
    @Past(message = "Date of birth must be a past date.")
    private LocalDate dob;
}