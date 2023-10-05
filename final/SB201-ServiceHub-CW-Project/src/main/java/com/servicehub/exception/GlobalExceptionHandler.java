package com.servicehub.exception;

import java.time.LocalDateTime;
import java.util.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


//Exception Handling ----

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetails> handleException404(NotFoundException ex, WebRequest request){
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(details,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateValueException.class)
	public ResponseEntity<ErrorDetails> duplicateExceptionHandle(DuplicateValueException ex, WebRequest request){
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(details,HttpStatus.BAD_REQUEST);
	}
	
	//Exception Handling for Front-end Validation : It will provide a suitable message
	// for the user when a make a HTTP Request on any end Point
	@ExceptionHandler(MethodArgumentNotValidException .class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler (MethodArgumentNotValidException manv,
			WebRequest request) {
		
		
		List<ObjectError> allErrors = manv.getAllErrors();
	    List<String> errorMessages = new ArrayList<>();

	    for (ObjectError error : allErrors) {
	        errorMessages.add(error.getDefaultMessage());
	    }

	    String joinedErrors = String.join(", ", errorMessages);

	    ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), joinedErrors, request.getDescription(false));

	    return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorDetails> IllegalStateExceptionHandler(IllegalStateException ex, WebRequest request){
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(details,HttpStatus.BAD_REQUEST);
	}
	
	
	
	

}
