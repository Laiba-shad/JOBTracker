package com.jobtrackr.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("JOBTrackr — Your Verification Code");
        message.setText(
                "Hi,\n\n" +
                        "Your verification code is: " + otp + "\n\n" +
                        "This code expires in 5 minutes.\n" +
                        "If you did not request this, ignore this email.\n\n" +
                        "— JOBTrackr Team"
        );
        mailSender.send(message);
    }
}