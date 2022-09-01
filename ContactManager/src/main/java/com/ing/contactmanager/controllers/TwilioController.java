package com.ing.contactmanager.controllers;

import com.ing.contactmanager.controllers.dtos.request.authentication.RequestAuthenticationCodeDTO;
import com.ing.contactmanager.security.authentication.AuthenticationFacade;
import com.ing.contactmanager.services.impl.PhoneVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/2fa-verification")
public class TwilioController {

    private final PhoneVerificationService phoneVerificationService;

    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public ResponseEntity<String> sendSmsToPhoneNumber(
            @RequestBody @Pattern(regexp = "/^\\+?[1-9][0-9]{7,14}$/") String phoneNumber) {


        return ResponseEntity.ok(phoneVerificationService.startVerification(phoneNumber,
                authenticationFacade.getEmailFromLoggedInUser()));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(
            @Valid @RequestBody RequestAuthenticationCodeDTO authenticationCodeDTO) {
        return ResponseEntity.ok(
                phoneVerificationService.checkCode(authenticationCodeDTO.getPhoneNumber(),
                        authenticationCodeDTO.getCode()));
    }
}
