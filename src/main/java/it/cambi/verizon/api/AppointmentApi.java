/**
 *
 */
package it.cambi.verizon.api;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;

import java.util.List;

/**
 * @author luca
 *
 */
public interface AppointmentApi {

    Appointment findAppointmentById(String id, AppointmentType type);

    List<Appointment> findAppointments();

    List<Appointment> findAppointmentsByDay(String day);

    List<Appointment> findAppointmentsOfAttendeeByDay(String day, String attendee);

    Appointment saveAppointment(Appointment appointment);

    Appointment updateAppointment(Appointment appointment);

    boolean deleteAppointment(Appointment appointment);
}
