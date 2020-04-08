/**
 *
 */
package it.cambi.verizon.service;

import it.cambi.verizon.domain.Appointment;

import java.util.List;

/**
 * @author luca
 *
 */
public interface AppointmentService
{

    /**
     * @return
     */
    List<Appointment> findAll();

    /**
     * @param _id
     * @return
     */
    Appointment findByObjectId(String _id);

    /**
     * @param day
     * @param attendee
     * @return
     */
    List<Appointment> findOfAttendeeByDay(String day, String attendee);

    /**
     * @param date
     * @return
     */
    List<Appointment> findByDay(String date);

    /**
     * @param meeting
     * @return
     */
    Appointment save(Appointment meeting);

    /**
     * @param meeting
     * @return
     */
    boolean delete(Appointment meeting);

}
