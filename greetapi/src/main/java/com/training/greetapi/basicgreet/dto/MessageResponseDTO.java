package com.training.greetapi.basicgreet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MessageResponseDTO {

	@Schema(name = "Id", example = "1", required = true)
	private Long id;
	@Schema(name = "creatorName", example = "Mary", required = true)
	@NotBlank (message = " creatorName should not be empty")
	private String creatorName;
	@Schema(name = "message", example = "wishing you a good day", required = true)
	@NotBlank (message = "message should not be empty")
	@Pattern(regexp = "^[a-zA-z\s]+[!]+}$")	
	private String message;
	@Schema(name = "description", example = "morning wish", required = true)
	@NotBlank (message = " description should not be empty")
	private String description;

}
