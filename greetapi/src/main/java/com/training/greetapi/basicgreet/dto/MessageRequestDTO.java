package com.training.greetapi.basicgreet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class MessageRequestDTO {

	@Schema(name = "creatorName", example = "Mary", required = true)
	@NotBlank (message = "creator name should not be empty")
	@Pattern(regexp = "^[[a-zA-z][0-9]_]+$")
	private String creatorName;
	@Schema(name = "message", example = "wishing you a good day", required = true)
	@NotBlank  (message = "greeting message should not be empty")
	@Pattern(regexp = "^[a-zA-z\\s]+[!]+$" ,message="greeting message : invalid characters")	
	private String greetingMessage;
	@Schema(name = "description", example = "morning wish", required = true)
	@NotBlank  (message = "description should not be empty")
	@Pattern(regexp = "^[a-zA-z\\s]+$")	
	private String description;
	
}
