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
public interface AppointmentApi
{

    public Appointment findAppointmentById(String id, AppointmentType type);

    public List<Appointment> findAppointments();

    public List<Appointment> findAppointmentsByDay(String day);

    public List<Appointment> findAppointmentsOfAttendeeByDay(String day, String attendee);

    public Appointment saveAppointment(Appointment appointment);

    public Appointment updateAppointment(Appointment appointment);

    public boolean deleteAppointment(Appointment appointment);

}
