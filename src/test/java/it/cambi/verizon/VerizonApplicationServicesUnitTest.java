package it.cambi.verizon;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.cambi.verizon.domain.*;
import it.cambi.verizon.service.AppointmentProxyService;
import it.cambi.verizon.service.MeetingService;
import it.cambi.verizon.service.ReminderService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class VerizonApplicationServicesUnitTest {

    @InjectMocks
    private AppointmentProxyService appointmentProxyService;

    @Mock
    private MeetingService meetingService;

    @Mock
    private ReminderService reminderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({"src/test/resources/meeting1.json,src/test/resources/reminder1.json"})
    public void should_findAppointments(String meeting1, String reminder1) throws IOException {

        when(meetingService.findAll()).thenReturn(new ArrayList<Appointment>() {{
            add(objectMapper.readValue(new File(meeting1), Meeting.class));
        }});

        when(reminderService.findAll()).thenReturn(new ArrayList<Appointment>() {{
            add(objectMapper.readValue(new File(reminder1), Reminder.class));
        }});

        List<Appointment> appointments = appointmentProxyService.findAppointments();

        assertEquals(2, appointments.size());
        verify(meetingService, times(1)).findAll();
        verify(reminderService, times(1)).findAll();

        assertEquals(1, appointments.stream().filter(a -> a.getType() == AppointmentType.MEETING).count());
        assertEquals(1, appointments.stream().filter(a -> a.getType() == AppointmentType.REMINDER).count());
    }

    @ParameterizedTest
    @CsvSource({"src/test/resources/meeting1.json,src/test/resources/reminder1.json"})
    public void should_findAppointmentsByDay(String meeting1, String reminder1) throws IOException {

        Meeting meeting = objectMapper.readValue(new File(meeting1), Meeting.class);

        Mockito.lenient().when(meetingService.findByDay(meeting.getDay())).thenReturn(new ArrayList<Appointment>() {{
            add(meeting);
        }});

        Reminder reminder = objectMapper.readValue(new File(reminder1), Reminder.class);

        Mockito.lenient().when(reminderService.findByDay(reminder.getDay())).thenReturn(new ArrayList<Appointment>() {{
            add(reminder);
        }});

        assertEquals(1, appointmentProxyService.findAppointmentsByDay(meeting.getDay()).size());
        assertEquals(1, appointmentProxyService.findAppointmentsByDay(reminder.getDay()).size());

    }

    @ParameterizedTest
    @CsvSource({"58d1c36efb0cac4e15afd278,src/test/resources/meeting2.json,src/test/resources/reminder2.json"})
    public void should_save_appointment(String objectId, String meeting2, String reminder2) throws IOException {

        /*
        Add Meeting
         */
        Meeting meeting = objectMapper.readValue(new File(meeting2), Meeting.class);

        ReflectionTestUtils.setField(meeting, "id", new ObjectId(objectId));

        when(meetingService.save(any(Meeting.class))).thenReturn(meeting);

        Appointment savedMeeting = appointmentProxyService.saveAppointment(meeting);
        verify(meetingService, times(1)).save(meeting);

        assertEquals(meeting, savedMeeting);

        /*
        Add Reminder
         */
        Reminder reminder = objectMapper.readValue(new File(reminder2), Reminder.class);

        ReflectionTestUtils.setField(reminder, "id", new ObjectId(objectId));

        when(reminderService.save(any(Reminder.class))).thenReturn(reminder);

        Appointment savedReminder = appointmentProxyService.saveAppointment(reminder);
        verify(reminderService, times(1)).save(reminder);

        assertEquals(reminder, savedReminder);


    }
}
