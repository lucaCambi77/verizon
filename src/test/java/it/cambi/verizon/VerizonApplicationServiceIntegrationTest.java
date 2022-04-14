package it.cambi.verizon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.cambi.verizon.domain.Address;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.domain.Reminder;
import it.cambi.verizon.service.AppointmentProxyService;

@SpringBootTest(classes = {ApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
public class VerizonApplicationServiceIntegrationTest {

  private @Autowired MongoTemplate mongoTemplate;

  private @Autowired AppointmentProxyService appointmentProxyService;

  private final DateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
  private final DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");

  @BeforeEach
  public void setUp() {
    mongoTemplate.dropCollection(Meeting.class);
    mongoTemplate.dropCollection(Reminder.class);
  }

  @Test
  void contextLoads() {
    assertNotNull(mongoTemplate);
  }

  @Test
  void should_create_appointments() {

    Date appointmentDate = new Date();

    Meeting meeting =
        Meeting.builder()
            .name("Meeting1")
            .day(dfDay.format(appointmentDate))
            .time(dfTime.format(appointmentDate))
            .address(
                Address.builder()
                    .city("Pistoia")
                    .country("Italy")
                    .street("Via via via")
                    .zipCode("1001")
                    .build())
            .attendees(new HashSet<>(Arrays.asList("pippo")))
            .type(AppointmentType.MEETING)
            .build();

    Meeting postedMeeting = (Meeting) appointmentProxyService.saveAppointment(meeting);

    assertNotNull(postedMeeting.getId());
    assertEquals(1, postedMeeting.getAttendees().size());

    Reminder reminder =
        Reminder.builder()
            .name("Reminder1")
            .day(dfDay.format(appointmentDate))
            .time(dfTime.format(appointmentDate))
            .address(
                Address.builder()
                    .city("Pistoia")
                    .country("Italy")
                    .street("Via via via")
                    .zipCode("1001")
                    .build())
            .type(AppointmentType.REMINDER)
            .build();

    Reminder postedReminder = (Reminder) appointmentProxyService.saveAppointment(reminder);

    assertNotNull(postedReminder.getId());

    List<Appointment> appointments = appointmentProxyService.findAppointments();

    assertEquals(2, appointments.size());
  }

  @ParameterizedTest
  @CsvSource({"2020-04-01, 00:00:00"})
  void should_find_appointments_of_attendee_by_day(String date, String time) {

    Meeting meeting =
        Meeting.builder()
            .name("Meeting1")
            .day(date)
            .time(time)
            .attendees(Set.of("pippo"))
            .type(AppointmentType.MEETING)
            .build();

    appointmentProxyService.saveAppointment(meeting);

    Meeting anotherMeeting =
        Meeting.builder()
            .name("Meeting2")
            .day(date)
            .time(time)
            .attendees(Set.of("pluto"))
            .type(AppointmentType.MEETING)
            .build();

    appointmentProxyService.saveAppointment(anotherMeeting);

    assertEquals(1, appointmentProxyService.findAppointmentsOfAttendeeByDay(date, "pippo").size());
    assertEquals(2, appointmentProxyService.findAppointmentsByDay(date).size());
  }

  @ParameterizedTest
  @CsvSource({"2020-04-01, 2020-04-02"})
  void should_update_meeting(String date, String date2) throws IOException {

    Meeting meeting =
        Meeting.builder()
            .name("Meeting1")
            .day(date)
            .attendees(Set.of("pippo"))
            .type(AppointmentType.MEETING)
            .build();

    appointmentProxyService.saveAppointment(meeting);

    Meeting meeting1 =
        (Meeting)
            appointmentProxyService.findAppointmentById(meeting.getId(), AppointmentType.MEETING);

    meeting1.setDay(date2);

    Meeting updatedMeeting = (Meeting) appointmentProxyService.saveAppointment(meeting1);

    assertEquals(date2, updatedMeeting.getDay());
    assertEquals(meeting, updatedMeeting);
  }
}
