/**
 * 
 */
package it.cambi.verizon.api;

import java.util.List;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;

/**
 * @author luca
 *
 */
public interface AppointmentApi
{

    public Appointment findAppointmentById(String id, AppointmentType type);

    public List<Appointment> findAppointments();

    public List<Appointment> findAppointmentsByDay(String day);

    public List<Appointment> findAppointmentsofAttendeeByDay(String day, String attendee);

    public Appointment saveAppointment(Appointment appointment);

    public Appointment updateAppointment(Appointment appointment);

    public boolean deleteAppointment(Appointment appointment);

}
