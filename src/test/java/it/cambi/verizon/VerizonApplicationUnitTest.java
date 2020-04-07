package it.cambi.verizon;

import it.cambi.verizon.domain.Address;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.domain.Reminder;
import it.cambi.verizon.mongo.repository.MeetingRepository;
import it.cambi.verizon.mongo.repository.ReminderRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class VerizonApplicationUnitTest {

    private AppointmentProxyService appointmentProxyService;

    private MeetingService meetingService;

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

    @Mock
    MeetingRepository meetingRepository;
    @Mock
    ReminderRepository reminderRepository;


    @BeforeEach
    public void setUp() {

        meetingService = new MeetingService(meetingRepository);
        reminderService = new ReminderService(reminderRepository);

        appointmentProxyService = new AppointmentProxyService(meetingService, reminderService);

        Mockito.lenient().when(meetingRepository.findAll()).thenReturn(new ArrayList<Meeting>() {{
            add(meeting);
        }});

        Mockito.lenient().when(reminderRepository.findAll()).thenReturn(new ArrayList<Reminder>() {{
            add(reminder);
        }});

        Mockito.lenient().when(meetingRepository.findAllByDay("20200401", true)).thenReturn(new ArrayList<Meeting>() {{
            add(meeting);
        }});

        Mockito.lenient().when(reminderRepository.findAllByDay("20200402", true)).thenReturn(new ArrayList<Reminder>() {{
            add(reminder);
        }});

    }

    @Test
    public void should_findAppointments() {

        List<Appointment> appointments = appointmentProxyService.findAppointments();

        assertEquals(2, appointments.size());
        verify(meetingRepository, times(1)).findAll();
        verify(reminderRepository, times(1)).findAll();

    }

    @Test
    public void should_findAppointmentsByDay() {
        assertEquals(1, appointmentProxyService.findAppointmentsByDay("20200401").size());
        assertEquals(1, appointmentProxyService.findAppointmentsByDay("20200402").size());

    }


}
