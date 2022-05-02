package spd.trello.service;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
@Service
public class ReminderAlertService {

    private static final Logger LOG = LoggerFactory.getLogger(ReminderAlertService.class);

    @Autowired
    MailService mailService;

    @Setter
    ReminderService service;

    @Scheduled(cron = "1 * * * * *")
    private void check()
    {
        LOG.info("Schedule executed!");
        List<Reminder> storage = service.getAllActive();
        LocalDateTime now = LocalDateTime.now();
        for (Reminder r :
                storage) {
            work(r);
            service.deactivate(r.getId());
        }
        storage.removeIf(r -> !r.getActive());
    }

    private void work(Reminder reminder)
    {
        mailService.send(reminder);
    }

}
