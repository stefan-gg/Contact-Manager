package com.ing.contactmanager.twilio;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private final TwilioProperties twilioProperties;

    @Autowired
    public TwilioInitializer(TwilioProperties twilioProperties){
        this.twilioProperties = twilioProperties;
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
    }

}
