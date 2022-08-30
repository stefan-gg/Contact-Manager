package com.ing.contactmanager.services.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Async
    public void sendConfirmationEmail(String to, String firstName, String lastName)
            throws IOException, TemplateException, MessagingException {

        Map<String, Object> data = new HashMap<>();
        data.put("firstName", firstName);
        data.put("lastName", lastName);

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("email-template.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom("zvonkobodanovic@outlook.com");
        mimeMessageHelper.setText(text);
        mimeMessageHelper.setSubject("Confirmation email");

        javaMailSender.send(message);
    }
}
