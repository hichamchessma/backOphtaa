package com.ophta.controller;

import lombok.extern.slf4j.Slf4j;
import com.ophta.entity.AuthenticationRequest;
import com.ophta.entity.AuthenticationResponse;
import com.ophta.entity.ForgotPasswordRequest;
import com.ophta.entity.RegistrationRequest;
import com.ophta.entity.ResetPasswordRequest;
import com.ophta.entity.User;
import com.ophta.service.CustomUserDetailsService;
import com.ophta.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        UserDetails userDetails;
        try {
            String identifier = request.getUsername(); // This could be either email or username
            String password = request.getPassword();

            // Check if the identifier is an email or username
            boolean isEmail = identifier.contains("@");

            // Log the type of authentication being attempted
            System.out.println("Authentication attempt with " + (isEmail ? "email" : "username") + ": " + identifier);


            if (isEmail) {
                // If it's an email, load user by email
                userDetails = userDetailsService.loadUserByEmail(identifier);
            } else {
                // If it's a username, use the existing method
                userDetails = userDetailsService.loadUserByUsername(identifier);
            }

            // Attempt authentication with the credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password)
            );
        } catch (Exception e) {
            throw new Exception("Invalid username/email or password", e);
        }

        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);
    }
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegistrationRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            userDetailsService.saveUser(request);
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            userDetailsService.sendPasswordResetEmail(request.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("message","Password reset email sent.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error sending password reset email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            String token = request.getToken();
            String newPassword = request.getNewPassword();

            userDetailsService.resetPassword(token, newPassword);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password successfully reset.");

            return ResponseEntity.ok(response);  // Return the map in the response
        } catch (Exception e) {
            log.error("Error in password reset process ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting password.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(Principal principal) {
        String username = principal.getName();
        User user = userDetailsService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
