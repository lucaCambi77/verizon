/** */
package it.cambi.verizon.service;

import it.cambi.verizon.domain.Appointment;

import java.util.List;

/** @author luca */
public interface AppointmentService {
  List<Appointment> findAll();

  Appointment findByObjectId(String _id);

  List<Appointment> findOfAttendeeByDay(String day, String attendee);

  List<Appointment> findByDay(String date);

  Appointment save(Appointment meeting);

  boolean delete(Appointment meeting);
}
