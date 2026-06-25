package com.nt.advice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nt.exceptions.InvalidSSNException;
import com.nt.exceptions.SsaWebApiException;

@RestControllerAdvice
public class CitizenAppRegistrationControllerAdvice {
	 @ExceptionHandler(InvalidSSNException.class)
	    public ResponseEntity<Map<String, Object>> handleInvalidSSN(InvalidSSNException exception) {

	        Map<String, Object> response = new LinkedHashMap<>();
	        response.put("timestamp", LocalDateTime.now());
	        response.put("status", HttpStatus.BAD_REQUEST.value());
	        response.put("error", "Bad Request");
	        response.put("message", exception.getMessage());

	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(SsaWebApiException.class)
	    public ResponseEntity<Map<String, Object>> handleSsaWebApiException(SsaWebApiException exception) {

	        Map<String, Object> response = new LinkedHashMap<>();
	        response.put("timestamp", LocalDateTime.now());
	        response.put("status", HttpStatus.BAD_GATEWAY.value());
	        response.put("error", "SSA Web API Error");
	        response.put("message", exception.getMessage());

	        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {

	        Map<String, String> fieldErrors = new LinkedHashMap<>();

	        exception.getBindingResult().getFieldErrors().forEach(error ->
	                fieldErrors.put(error.getField(), error.getDefaultMessage())
	        );

	        Map<String, Object> response = new LinkedHashMap<>();
	        response.put("timestamp", LocalDateTime.now());
	        response.put("status", HttpStatus.BAD_REQUEST.value());
	        response.put("error", "Validation Failed");
	        response.put("messages", fieldErrors);

	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

		/*@ExceptionHandler(Exception.class)
		public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception exception) {
		
		    Map<String, Object> response = new LinkedHashMap<>();
		    response.put("timestamp", LocalDateTime.now());
		    response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		    response.put("error", "Internal Server Error");
		    response.put("message", "Something went wrong. Please try again later.");
		
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}*/
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, Object>> handleAllExceptions(
	            Exception exception) {

	        // Print the complete exception in the application console.
	        exception.printStackTrace();

	        Map<String, Object> response = new LinkedHashMap<>();

	        response.put("timestamp", LocalDateTime.now());
	        response.put(
	                "status",
	                HttpStatus.INTERNAL_SERVER_ERROR.value()
	        );
	        response.put(
	                "error",
	                "Internal Server Error"
	        );
	        response.put(
	                "exception",
	                exception.getClass().getSimpleName()
	        );
	        response.put(
	                "message",
	                exception.getMessage()
	        );

	        return new ResponseEntity<>(
	                response,
	                HttpStatus.INTERNAL_SERVER_ERROR
	        );
	    }

}
