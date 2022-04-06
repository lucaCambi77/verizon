/**
 *
 */
package it.cambi.verizon.service;

import java.util.List;

import it.cambi.verizon.domain.Appointment;

/** @author luca */
public interface AppointmentService {
    List<Appointment> findAll();

    Appointment findByObjectId(String _id);

    List<Appointment> findOfAttendeeByDay(String day, String attendee);

    List<Appointment> findByDay(String date);

    Appointment save(Appointment meeting);

    void delete(Appointment meeting);
}
