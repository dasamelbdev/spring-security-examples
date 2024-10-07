package com.training.greetapi.shared.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApiError {

	@Schema(name = "statusCode", example = "400", required = true)
	private int statusCode;
	@Schema(name = "message", example = "provided data not in correct format", required = true)
	private String message;

}
