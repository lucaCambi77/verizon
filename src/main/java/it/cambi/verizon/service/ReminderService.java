/** */
package it.cambi.verizon.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Reminder;
import it.cambi.verizon.mongo.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** @author luca */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService implements AppointmentService {

  private final ReminderRepository reminderRepository;

  public List<Appointment> findAll() {

    return new ArrayList<>(reminderRepository.findAll());
  }

  public Appointment findByObjectId(String _id) {

    return reminderRepository.findOneById(new ObjectId(_id));
  }

  public List<Appointment> findOfAttendeeByDay(String day, String attendee) {

    return null;
  }

  public List<Appointment> findByDay(String date) {

    return new ArrayList<>(reminderRepository.findByDay(date));
  }

  public Reminder save(Appointment reminder) {
    log.info("... creating or updating reminder " + reminder.getName());

    return reminderRepository.save((Reminder) reminder);
  }

  public void delete(Appointment reminder) {
    log.info("... deleting reminder " + reminder.getId());

    reminderRepository.delete((Reminder) reminder);
  }
}
