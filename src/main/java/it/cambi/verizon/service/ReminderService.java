/**
 * 
 */
package it.cambi.verizon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Reminder;
import it.cambi.verizon.mongo.repository.ReminderRepository;

/**
 * @author luca
 *
 */
@Service
public class ReminderService implements AppointmentService
{
    private static final Logger log = LoggerFactory.getLogger(ReminderService.class);

    private @Autowired ReminderRepository reminderRepository;

    public List<Appointment> findAll()
    {

        return new ArrayList<Appointment>(reminderRepository.findAll());
    }

    public Appointment findByObjectId(String _id)
    {

        return reminderRepository.findOneBy_id(new ObjectId(_id));
    }

    public List<Appointment> findOfAttendeeByDay(String day, String attendee)
    {

        return null;
    }

    public List<Appointment> findByDay(String date)
    {

        return reminderRepository.findAllByDay(date).stream().filter(m -> m.isConfirmed()).collect(Collectors.toList());

    }

    public Reminder save(Appointment reminder)
    {
        log.info("... creating or updating reminder " + reminder.getName());

        return reminderRepository.save((Reminder) reminder);
    }

    public boolean delete(Appointment reminder)
    {
        log.info("... deleting reminder " + reminder.get_id());

        reminder.setConfirmed(false);

        return !reminderRepository.save((Reminder) reminder).isConfirmed();
    }
}
