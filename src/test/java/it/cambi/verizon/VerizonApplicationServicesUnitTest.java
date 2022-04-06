package it.cambi.verizon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.cambi.verizon.domain.Address;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.domain.Reminder;
import it.cambi.verizon.service.AppointmentProxyService;
import it.cambi.verizon.service.MeetingService;
import it.cambi.verizon.service.ReminderService;

@ExtendWith(MockitoExtension.class)
public class VerizonApplicationServicesUnitTest {

  @InjectMocks private AppointmentProxyService appointmentProxyService;

  @Mock private MeetingService meetingService;

  @Mock private ReminderService reminderService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void should_findAppointments() {

    String date = "2020-04-01";
    String time = "00:00:00";

    when(meetingService.findAll())
        .thenReturn(
            List.of(
                Meeting.builder()
                    .name("Meeting1")
                    .day(date)
                    .time(time)
                    .attendees(Set.of("pippo"))
                    .type(AppointmentType.MEETING)
                    .build()));

    when(reminderService.findAll())
        .thenReturn(
            List.of(
                Reminder.builder()
                    .name("Reminder1")
                    .day(date)
                    .time(time)
                    .address(
                        Address.builder()
                            .city("Pistoia")
                            .country("Italy")
                            .street("Via via via")
                            .zipCode("1001")
                            .build())
                    .type(AppointmentType.REMINDER)
                    .build()));

    List<Appointment> appointments = appointmentProxyService.findAppointments();

    assertEquals(2, appointments.size());
    verify(meetingService, times(1)).findAll();
    verify(reminderService, times(1)).findAll();

    assertEquals(
        1, appointments.stream().filter(a -> a.getType() == AppointmentType.MEETING).count());
    assertEquals(
        1, appointments.stream().filter(a -> a.getType() == AppointmentType.REMINDER).count());
  }

  @Test
  public void should_findAppointmentsByDay() {

    String day = "2020-04-01";
    String day1 = "2020-04-02";
    String time = "00:00:00";

    lenient()
        .when(meetingService.findByDay(day))
        .thenReturn(
            List.of(
                Meeting.builder()
                    .name("Meeting1")
                    .day(day)
                    .time(time)
                    .attendees(Set.of("pippo"))
                    .type(AppointmentType.MEETING)
                    .build()));

    lenient()
        .when(reminderService.findByDay(day1))
        .thenReturn(
            List.of(
                Reminder.builder()
                    .name("Reminder1")
                    .day(day1)
                    .time(time)
                    .type(AppointmentType.REMINDER)
                    .build()));

    assertEquals(1, appointmentProxyService.findAppointmentsByDay(day).size());
    assertEquals(1, appointmentProxyService.findAppointmentsByDay(day1).size());
  }

  @ParameterizedTest
  @CsvSource({"58d1c36efb0cac4e15afd278"})
  public void should_save_appointment(String objectId) {

    String day = "2020-04-01";
    String time = "00:00:00";

    Meeting meeting =
        Meeting.builder()
            .name("Meeting1")
            .day(day)
            .time(time)
            .attendees(Set.of("pippo"))
            .type(AppointmentType.MEETING)
            .build();

    ReflectionTestUtils.setField(meeting, "id", new ObjectId(objectId));

    when(meetingService.save(any(Meeting.class))).thenReturn(meeting);

    Appointment savedMeeting = appointmentProxyService.saveAppointment(meeting);
    verify(meetingService, times(1)).save(meeting);

    assertEquals(meeting, savedMeeting);

    Reminder reminder =
        Reminder.builder()
            .name("Reminder1")
            .day(day)
            .time(time)
            .type(AppointmentType.REMINDER)
            .build();

    ReflectionTestUtils.setField(reminder, "id", new ObjectId(objectId));

    when(reminderService.save(any(Reminder.class))).thenReturn(reminder);

    Appointment savedReminder = appointmentProxyService.saveAppointment(reminder);
    verify(reminderService, times(1)).save(reminder);

    assertEquals(reminder, savedReminder);
  }
}
