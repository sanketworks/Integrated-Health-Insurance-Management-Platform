package com.nt.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-web-api")
public class SSAWebOperationsRestController {

    @GetMapping("/find/{ssn}")
    public ResponseEntity<String> getStateBySSN(
            @PathVariable Long ssn) {

        // Validate that SSN contains exactly 9 digits.
        if (ssn == null ||
                ssn < 100_000_000L ||
                ssn > 999_999_999L) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                        "Invalid SSN. SSN must contain exactly 9 digits."
                    );
        }

        // Extract the last two digits without converting to String.
        int stateCode = (int) (ssn % 100);

        return switch (stateCode) {

            case 1 -> ResponseEntity.ok("Washington DC");

            case 2 -> ResponseEntity.ok("Ohio");

            case 3 -> ResponseEntity.ok("Texas");

            case 4 -> ResponseEntity.ok("California");

            case 5 -> ResponseEntity.ok("Florida");

            default -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid SSN state code.");
        };
    }
}