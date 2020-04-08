package it.cambi.verizon;

import it.cambi.verizon.domain.*;
import it.cambi.verizon.service.AppointmentProxyService;
import it.cambi.verizon.service.MeetingService;
import it.cambi.verizon.service.ReminderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class VerizonApplicationUnitTest {

    @InjectMocks
    private AppointmentProxyService appointmentProxyService;

    @Mock
    private MeetingService meetingService;

    @Mock
    private ReminderService reminderService;

    private static Meeting meeting = new Meeting.Builder()
            .withName("Meeting 1")
            .withAddress(
                    new Address.Builder()
                            .withCity("Pistoia")
                            .withStreet("via Trento 12")
                            .build())
            .withDay("20200401")
            .withConfermation(true)
            .build();

    private static Reminder reminder = new Reminder.Builder()
            .withName("Reminder 1")
            .withAddress(
                    new Address.Builder()
                            .withCity("Firenze")
                            .withStreet("via Torino 12")
                            .build())
            .withDay("20200402")
            .withConfermation(true)
            .build();

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void should_findAppointments() {

        when(meetingService.findAll()).thenReturn(new ArrayList<Appointment>() {{
            add(meeting);
        }});

        when(reminderService.findAll()).thenReturn(new ArrayList<Appointment>() {{
            add(reminder);
        }});

        List<Appointment> appointments = appointmentProxyService.findAppointments();

        assertEquals(2, appointments.size());
        verify(meetingService, times(1)).findAll();
        verify(reminderService, times(1)).findAll();

        assertEquals(1, appointments.stream().filter(a -> a.getType() == AppointmentType.MEETING).count());
        assertEquals(1, appointments.stream().filter(a -> a.getType() == AppointmentType.REMINDER).count());
    }

    @Test
    public void should_findAppointmentsByDay() {

        Mockito.lenient().when(meetingService.findByDay("20200401")).thenReturn(new ArrayList<Appointment>() {{
            add(meeting);
        }});

        Mockito.lenient().when(reminderService.findByDay("20200402")).thenReturn(new ArrayList<Appointment>() {{
            add(reminder);
        }});

        assertEquals(1, appointmentProxyService.findAppointmentsByDay("20200401").size());
        assertEquals(1, appointmentProxyService.findAppointmentsByDay("20200402").size());

    }

    @Test
    public void should_save_appointment() {

        Meeting meeting = new Meeting.Builder()
                .withName("Meeting 2")
                .withAddress(
                        new Address.Builder()
                                .withCity("Milano")
                                .withStreet("via Genova 12")
                                .build())
                .withDay("20200403")
                .withConfermation(true)
                .build();

        when(meetingService.save(any(Meeting.class))).thenReturn(meeting);

        assertEquals(meeting.getName(), appointmentProxyService.saveAppointment(meeting).getName());
        assertEquals(meeting.getDay(), appointmentProxyService.saveAppointment(meeting).getDay());

        Reminder reminder = new Reminder.Builder()
                .withName("Reminder 2")
                .withAddress(
                        new Address.Builder()
                                .withCity("Milano")
                                .withStreet("via Genova 12")
                                .build())
                .withDay("20200403")
                .withConfermation(true)
                .build();

        when(reminderService.save(any(Reminder.class))).thenReturn(reminder);

        assertEquals(reminder.getName(), appointmentProxyService.saveAppointment(reminder).getName());
        assertEquals(reminder.getDay(), appointmentProxyService.saveAppointment(reminder).getDay());

    }
}
