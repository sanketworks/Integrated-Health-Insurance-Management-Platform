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
	public ResponseEntity<String> getStateBySSN(@PathVariable Integer ssn){
		if(String.valueOf(ssn).length()!=9)
			return new ResponseEntity<>("invalid ssn",HttpStatus.BAD_REQUEST);
		
		//get the state name
		int stateCode=ssn%100;
		String stateName=null;
		switch(stateCode) {
		case 01:
			stateName="Washington DC";
			break;
		case 02:
			stateName="Ohio";
			break;
		case 03:
			stateName="Texas";
			break;
		case 04:
			stateName="California";
			break;
		case 05:
			stateName="Florida";
			break;
		default:
			stateName="Invalid SSN";
		}
		return new ResponseEntity<>(stateName,HttpStatus.OK);
	}

}
