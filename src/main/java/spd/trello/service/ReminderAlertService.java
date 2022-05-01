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
import java.util.TreeSet;
@Service
public class ReminderAlertService {

    private static final Logger LOG = LoggerFactory.getLogger(ReminderAlertService.class);

    @Autowired
    MailService mailService;

    TreeSet<Reminder> storage;

    @Setter
    ReminderService service;

    Comparator<Reminder> comparator = new Comparator<Reminder>() {
        @Override
        public int compare(Reminder o1, Reminder o2) {
            return o1.getRemindOn().compareTo(o2.getRemindOn());
        }
    };



    @PostConstruct
    private void init()
    {
        storage = new TreeSet<Reminder>(comparator);
    }
    @Scheduled(cron = "1 * * * * *")
    private void check()
    {
        LOG.warn("Schedule executed!");
        LocalDateTime now = LocalDateTime.now();
        for (Reminder r :
                storage) {
            if (ChronoUnit.MINUTES.between(now, r.getRemindOn()) > 10)
            {
                LOG.warn("ReminderCycle stopped!");
                break;
            }
            r.setActive(false);
            work(r);
            service.deactivate(r.getId());
        }
        storage.removeIf(r -> !r.getActive());
    }

    private void work(Reminder reminder)
    {
        mailService.send(reminder);
    }

    public void addAll(Collection<Reminder> list)
    {
        storage.addAll(list);
        LOG.warn("Added "+list.size()+" reminders");
    }

    public void add(Reminder reminder)
    {
        storage.add(reminder);
    }

    public void remove(Reminder reminder)
    {
        storage.remove(reminder);
    }

    public void replace(Reminder old, Reminder _new)
    {
        storage.remove(old);
        storage.add(_new);
    }

}
