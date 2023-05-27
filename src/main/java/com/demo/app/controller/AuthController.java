package com.demo.app.controller;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;
import com.demo.app.dto.auth.RegisterRequest;
import com.demo.app.dto.message.ErrorResponse;
import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(path = "/api/v1/auth")
@Tag(name = "Authentication", description = "Login and Register User's account")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Sign in",
            description = "Sign in with username and password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sign In Successfully !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized / Invalid Token !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PostMapping(path = "/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final AuthenticationRequest request) {
        AuthenticationResponse authResponse = authService.login(request);
        return ResponseEntity.ok()
                .header("token", authResponse.getAccessToken())
                .body(new ResponseMessage("Sign in successfully !"));
    }

    @Operation(
            summary = "Sign up",
            description = "Sign up with username,email and password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "There are group of information need to user register",
                    content = @Content(
                            mediaType = "json/application",
                            schema = @Schema(
                                    implementation = RegisterRequest.class,
                                    example = ""
                            ),
                            examples = @ExampleObject(
                                    description = "",
                                    value = ""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sign Up Successfully !",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject())
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Username or email already taken !",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(path = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid final RegisterRequest registerRequest, final HttpServletRequest request) throws FieldExistedException {
        var authResponse = authService.register(registerRequest, request);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping(path = "/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

}
