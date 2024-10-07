package com.training.greetapi.basicgreet.controller;

import com.training.greetapi.basicgreet.dto.MessageRequestDTO;
import com.training.greetapi.basicgreet.dto.MessageResponseDTO;
import com.training.greetapi.basicgreet.service.MessageService;
import com.training.greetapi.basicgreet.entity.UserEntity;
import com.training.greetapi.shared.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Greetings API", description = "Greeting  management API")
@RestController
@RequestMapping("${api.messages.base.path}")
@Validated
public class BasicGreetController {

    private static final Logger logger = LoggerFactory.getLogger(BasicGreetController.class);
    private final MessageService messageService;

    public BasicGreetController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "find a message using a message id", description = "find a message. When message id is provided corresponding message returned.", tags = {
            "messages"})

    @ApiResponses({@ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MessageResponseDTO.class), mediaType = "application/json")}),
             @ApiResponse(responseCode = "400", description = "Incorrect format for name", content = {
                    @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")})

    })

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> findMessageById(
            @Parameter(example = "10", description = "id of the message", required = true) @Min(value = 1) @Max(value = 900000, message = "incorrect id range") @PathVariable(value = "id") Long id) {

        MessageResponseDTO messageResponseDTO = messageService.findMessageById(id);
        logger.info("find message by id {}", id);
        return new ResponseEntity<>(messageResponseDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<MessageResponseDTO>> findAllMessages(Pageable pageable, Authentication auth) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        //same can be done via the Authentication type parameter
        logger.info("username-" + userName);
        return new ResponseEntity<>(messageService.findAllMessages(pageable), HttpStatus.OK);
    }


    @Operation(summary = "create a greeting message", description = "create a greeting message using the provided details")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(schema = @Schema(implementation = MessageRequestDTO.class), mediaType = "application/json")}),

                    @ApiResponse(responseCode = "400", description = "Incorrect format for name", content = {
                            @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")})

            }
    )

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponseDTO> createGreeting(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "data for creating a greeting", required = true) @Valid @RequestBody MessageRequestDTO messageRequest) {

        MessageResponseDTO response = messageService.saveMessage(messageRequest);
        return new ResponseEntity<MessageResponseDTO>(response, HttpStatus.CREATED);

    }


    @Operation(summary = "update a greeting message", description = "update a greeting message using the provided details")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = MessageResponseDTO.class), mediaType = "application/json")}),

                    @ApiResponse(responseCode = "400", description = "Incorrect format for name", content = {
                            @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")})

            }
    )

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponseDTO> updateMessage(
            @Parameter(example = "10", description = "id of the message to be updated", required = true) @PathVariable(value = "id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "data for updating a message", required = true) @Valid @RequestBody MessageRequestDTO messageRequest) {

        MessageResponseDTO response = messageService.updateMessage(id, messageRequest);
        return new ResponseEntity<MessageResponseDTO>(response, HttpStatus.CREATED);

    }


    @Operation(summary = "delete a message using a message id", description = "delete a message. When message id is provided corresponding message returned.", tags = {
            "messages"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MessageResponseDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Incorrect format for name", content = {
                    @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")})

    })

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteMessageById(
            @Parameter(example = "10", description = "id of the message to be deleted", required = true) @PathVariable(value = "id") Long id) {
        return new ResponseEntity<Boolean>(messageService.deleteMessageById(id), HttpStatus.OK);
    }


    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Page<UserEntity>> findAllUsers(Pageable pageable) {
        return new ResponseEntity<>(messageService.findAllUsers(pageable), HttpStatus.OK);
    }



    /*
     * @GetMapping(value = "/basicgreet/{name}", produces = "text/plain") public
     * ResponseEntity<String> performBasicGreetlnPlainText(@PathVariable("name")
     * String personName) { return new ResponseEntity<String>(greetingMessage +
     * personName + " TEXT version", HttpStatus.OK); }
     */


    @PostMapping("/add")
    public String postEndpointA() {
        return "Works! post /add";
    }

    @GetMapping("/add/{id}")
    public String getEndpointA(@PathVariable String id, Authentication authentication) {
        return "Works! get /add";
    }

    @GetMapping("/add/bbb")
    public String getEnpointB() {
        return "Works! get /add/bbb";
    }

    @GetMapping("/add/bbb/ccc")
    public String getEnpointC() {
        return "Works! get /add/bbb/ccc";
    }
}
