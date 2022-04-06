/**
 *
 */
package it.cambi.verizon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;
import lombok.RequiredArgsConstructor;

/**
 * @author luca
 *
 */
@Service
@RequiredArgsConstructor
public class AppointmentProxyService {

    private final MeetingService meetingService;
    private final ReminderService reminderService;

    public Appointment findAppointmentById(String id, AppointmentType type) {
        return getService(type).findByObjectId(id);
    }

    public List<Appointment> findAppointments() {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.addAll(meetingService.findAll());
        appointments.addAll(reminderService.findAll());

        return appointments;
    }

    public List<Appointment> findAppointmentsByDay(String day) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.addAll(meetingService.findByDay(day));
        appointments.addAll(reminderService.findByDay(day));

        return appointments;
    }

    public List<Appointment> findAppointmentsOfAttendeeByDay(String day, String attendee) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.addAll(meetingService.findOfAttendeeByDay(day, attendee));

        return appointments;
    }

    public Appointment saveAppointment(Appointment appointment) {
        return getService(appointment.getType()).save(appointment);
    }

    public void deleteAppointment(Appointment appointment) {
        getService(appointment.getType()).delete(appointment);
    }

    private AppointmentService getService(AppointmentType type) {
        if (type == AppointmentType.MEETING) {
            return meetingService;
        }
        return reminderService;
    }
}
