//Made by GRPTeam05
package com.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String receiver, String subject, String content){
        SimpleMailMessage newMessage = new SimpleMailMessage();
        newMessage.setFrom("scyxw10@gamil.com");
        newMessage.setTo(receiver);
        newMessage.setSubject(subject);
        newMessage.setText(content);
        emailSender.send(newMessage);
    }
}
