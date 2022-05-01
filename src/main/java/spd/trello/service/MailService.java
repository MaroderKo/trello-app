package spd.trello.service;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;

import java.util.Properties;

@Service
public class MailService {

    @Autowired
    JavaMailSender sender;

    public void send(Reminder reminder)
    {
        System.out.println("Sending message");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Dex@gmail.com");
        message.setTo("worker@gmail.com");
        message.setText("Message from reminder "+reminder.getId());
        sender.send(message);
    }



}
