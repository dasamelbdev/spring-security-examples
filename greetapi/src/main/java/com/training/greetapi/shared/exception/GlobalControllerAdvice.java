package com.training.greetapi.shared.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception,
			WebRequest request) {

		String requestSent = request.getDescription(false);
		StringBuilder content = new StringBuilder();
		content.append("Request :" + requestSent).append(",\n");
		for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
			content.append("Invalid value provided :" + violation.getInvalidValue() + "," + "\n");
			content.append("message :" + violation.getMessage() + "\n");

		}

		ApiError apiError = new ApiError();
		apiError.setMessage(content.toString());
		apiError.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return super.handleExceptionInternal(exception, apiError, null, HttpStatus.BAD_REQUEST, request);
	
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		ApiError apiError = new ApiError();
		apiError.setMessage(ex.getLocalizedMessage());
		apiError.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

}
