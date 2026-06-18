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
	public ResponseEntity<String> getStateBySSN(@PathVariable String ssn){
		// Validate SSN: exactly 9 digits only
        if (ssn == null || !ssn.matches("\\d{9}")) {
            return new ResponseEntity<>("Invalid SSN. SSN must contain exactly 9 digits.", HttpStatus.BAD_REQUEST);
        }
        // Get last two digits
        String stateCode = ssn.substring(ssn.length() - 2);
        switch (stateCode) {
        case "01":
            return ResponseEntity.ok("Washington DC");

        case "02":
            return ResponseEntity.ok("Ohio");

        case "03":
            return ResponseEntity.ok("Texas");

        case "04":
            return ResponseEntity.ok("California");

        case "05":
            return ResponseEntity.ok("Florida");

        default:
            return new ResponseEntity<>("Invalid SSN state code.", HttpStatus.BAD_REQUEST);
    }
		
	}

}
