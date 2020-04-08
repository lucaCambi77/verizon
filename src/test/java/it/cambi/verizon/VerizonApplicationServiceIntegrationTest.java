package it.cambi.verizon;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.cambi.verizon.domain.*;
import it.cambi.verizon.service.AppointmentProxyService;
import it.cambi.verizon.service.AttendeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
public class VerizonApplicationServiceIntegrationTest {

    private @Autowired
    MongoTemplate mongoTemplate;

    private @Autowired
    AttendeeService attendeeService;

    private @Autowired
    AppointmentProxyService appointmentProxyService;

    private @Autowired
    ObjectMapper objectMapper;

    private DateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection(Meeting.class);
        mongoTemplate.dropCollection(Reminder.class);
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertNotNull(mongoTemplate);
    }

    @ParameterizedTest
    @CsvSource({"src/test/resources/attendee.json"})
    void should_create_attendee(String attendee) throws IOException {

        Attendee result = attendeeService.save(objectMapper.readValue(new File(attendee), Attendee.class));

        assertNotNull(result.getId());

    }

    @ParameterizedTest
    @CsvSource({"src/test/resources/attendee.json"})
    void should_create_appointments(String attendee) throws IOException {
        Attendee result = attendeeService.save(objectMapper.readValue(new File(attendee), Attendee.class));

        Date appointmentDate = new Date();

        @SuppressWarnings("serial")
        Meeting meeting = new Meeting.Builder()
                .withName("Meeting1")
                .withDay(dfDay.format(appointmentDate))
                .withTime(dfTime.format(appointmentDate))
                .withAddress(new Address.Builder()
                        .withCity("Pistoia")
                        .withCountry("Italy")
                        .withStreet("Via via via")
                        .withZipCode("1001")
                        .build())
                .withConfermation(true)
                .withAttendees(new HashSet<String>() {
                    {
                        add(result.getId());
                    }
                }).build();

        Meeting postedMeeting = (Meeting) appointmentProxyService.saveAppointment(meeting);

        assertNotNull(postedMeeting.getId());
        assertEquals(1, postedMeeting.getAttendees().size());

        Reminder reminder = new Reminder.Builder().withName("Reminder1").withDay(dfDay.format(appointmentDate))
                .withTime(dfTime.format(appointmentDate))
                .withConfermation(true)
                .withAddress(new Address.Builder()
                        .withCity("Pistoia")
                        .withCountry("Italy")
                        .withStreet("Via via via")
                        .withZipCode("1001")
                        .build())
                .build();

        Reminder postedReminder = (Reminder) appointmentProxyService.saveAppointment(reminder);

        assertNotNull(postedReminder.getId());

        List<Appointment> appointments = appointmentProxyService.findAppointments();

        assertEquals(2, appointments.size());
    }

    @ParameterizedTest
    @CsvSource({"src/test/resources/attendee.json,src/test/resources/meeting1.json,src/test/resources/meeting2.json, 2020-04-01"})
    void should_find_appointments_of_attendee_by_day(String attendee, String meeting1, String meeting2, String date) throws IOException {

        Attendee aAttendee = attendeeService.save(objectMapper.readValue(new File(attendee), Attendee.class));

        Meeting aMeeting = objectMapper.readValue(new File(meeting1), Meeting.class);
        aMeeting.setDay(date);
        aMeeting.setAttendees(new HashSet<String>() {
            {
                add(aAttendee.getId());
            }
        });
        appointmentProxyService.saveAppointment(aMeeting);

        Meeting anotherMeeting = objectMapper.readValue(new File(meeting2), Meeting.class);
        anotherMeeting.setDay(date);

        appointmentProxyService.saveAppointment(anotherMeeting);

        assertEquals(1, appointmentProxyService.findAppointmentsofAttendeeByDay(date, aAttendee.getId()).size());
        assertEquals(2, appointmentProxyService.findAppointmentsByDay(date).size());

    }

    @ParameterizedTest
    @CsvSource({"src/test/resources/attendee.json,src/test/resources/meeting1.json, 2020-04-01, 2020-04-02"})
    void should_update_meeting(String attendee, String meeting1, String date, String date2) throws IOException {

        Attendee aAttendee = attendeeService.save(objectMapper.readValue(new File(attendee), Attendee.class));

        Meeting aMeeting = objectMapper.readValue(new File(meeting1), Meeting.class);
        aMeeting.setDay(date);
        aMeeting.setAttendees(new HashSet<String>() {
            {
                add(aAttendee.getId());
            }
        });

        appointmentProxyService.saveAppointment(aMeeting);

        Meeting meeting = (Meeting) appointmentProxyService.findAppointmentById(aMeeting.getId(), AppointmentType.MEETING);
        meeting.setDay(date2);

        Meeting updatedMeeting = (Meeting) appointmentProxyService.saveAppointment(meeting);

        assertEquals(date2, updatedMeeting.getDay());
        assertEquals(meeting, updatedMeeting);

    }

}
