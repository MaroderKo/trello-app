package spd.trello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class ReminderService extends AbstractParentBasedService<Reminder, ReminderRepository>{

    private static final Logger LOG = LoggerFactory.getLogger(ReminderService.class);

    @Autowired
    ReminderAlertService alertService;

    @PostConstruct
    private void init()
    {
        alertService.setService(this);
        alertService.addAll(getAllActive());
        LOG.warn("ReminderAlertService constructed");
    }

    public ReminderService(ReminderRepository repository) {
        super(repository);
    }

    public List<Reminder> getAllActive()
    {
        return repository.findActive();
    }

    public void deactivate(UUID r) {
        LOG.warn("Reminder "+r+" deactivated in DB!");
        repository.deactivate(r);
    }
}
