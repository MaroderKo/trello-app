package spd.trello.service;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MailService {

    AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    JavaMailSender sender;

    @Autowired
    ThreadPoolExecutor executor;

    public void send(Reminder reminder)
    {
        executor.submit(() ->
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Dex@gmail.com");
            message.setTo("worker@gmail.com");
            message.setText("Message from reminder "+reminder.getId());
            sender.send(message);
            counter.incrementAndGet();
        });

    }



}
