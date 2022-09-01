package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.VerificationStatus;
import com.ing.contactmanager.twilio.TwilioProperties;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

    private final TwilioProperties twilioProperties;

    private final UserServiceImpl userService;

    @Autowired
    public PhoneVerificationService(TwilioProperties twilioProperties, UserServiceImpl userService) {
        this.twilioProperties = twilioProperties;
        this.userService = userService;
    }

    public String startVerification(String phone, String email) {
        Verification verification =
                Verification.creator(twilioProperties.getServiceSid(), phone, "sms").create();

        if(!verification.getStatus().equals("canceled")){
            User user = userService.getUserByEmail(email);
            user.setVerificationStatus(VerificationStatus.UNVERIFIED);

            userService.updateUser(user);
        } else {
            throw new ApiException("Request is canceled.");
        }

        return verification.getStatus();
    }

    public String checkCode(String phone, String code) {
        VerificationCheck verificationCheck =
                VerificationCheck
                        .creator(twilioProperties
                                .getServiceSid())
                        .setTo(phone)
                        .setCode(code)
                        .create();

        return verificationCheck.getStatus();
    }
}