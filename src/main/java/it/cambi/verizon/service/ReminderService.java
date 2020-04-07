/**
 *
 */
package it.cambi.verizon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class ReminderService implements AppointmentService {

    private final ReminderRepository reminderRepository;

    public List<Appointment> findAll() {

        return new ArrayList<Appointment>(reminderRepository.findAll());
    }

    public Appointment findByObjectId(String _id) {

        return reminderRepository.findOneById(new ObjectId(_id));
    }

    public List<Appointment> findOfAttendeeByDay(String day, String attendee) {

        return null;
    }

    public List<Appointment> findByDay(String date) {

        return new ArrayList<Appointment>(reminderRepository.findAllByDay(date, true));

    }

    public Reminder save(Appointment reminder) {
        log.info("... creating or updating reminder " + reminder.getName());

        return reminderRepository.save((Reminder) reminder);
    }

    public boolean delete(Appointment reminder) {
        log.info("... deleting reminder " + reminder.getId());

        reminder.setConfirmed(false);

        return !reminderRepository.save((Reminder) reminder).isConfirmed();
    }
}
