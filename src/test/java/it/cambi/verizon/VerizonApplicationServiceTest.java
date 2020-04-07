package it.cambi.verizon;

import it.cambi.verizon.domain.*;
import it.cambi.verizon.service.AppointmentProxyService;
import it.cambi.verizon.service.AttendeeService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = { VerizonApplication.class, ApplicationConfigurationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles({ "test" })
public class VerizonApplicationServiceTest
{

    private @Autowired MongoTemplate mongoTemplate;
    private @Autowired AttendeeService attendeeService;
    private @Autowired AppointmentProxyService appointmentProxyService;

    private static String attendeeId;
    private static String meetingId;

    private static Date appointmentDate = new Date();

    private DateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");

    @Test
    @Order(1)
    void contextLoads()
    {

        assertNotNull(mongoTemplate);
        mongoTemplate.dropCollection(Meeting.class);
        mongoTemplate.dropCollection(Reminder.class);
    }

    @Test
    @Order(2)
    void should_create_attendee()
    {
        Address address = new Address.Builder().withCity("Pistoia").withCountry("Italy").withStreet("Via via").withZipCode("1000").build();
        Attendee attendee = new Attendee.Builder().withName("Luca").withSurname("Cambi").withAddress(address).withEmail("luca.cambi@xxx.com").build();

        Attendee result = attendeeService.save(attendee);

        assertNotNull(result.getId());
        attendeeId = result.getId();
    }

    @Test
    @Order(3)
    void should_create_appointments()
    {

        Address address = new Address.Builder().withCity("Pistoia").withCountry("Italy").withStreet("Via via via").withZipCode("1001").build();

        @SuppressWarnings("serial")
        Meeting meeting = new Meeting.Builder().withName("Meeting1").withDay(dfDay.format(appointmentDate)).withTime(dfTime.format(appointmentDate))
                .withAddress(address).withConfermation(true)
                .withAttendees(new HashSet<String>()
                {
                    {
                        add(attendeeId);
                    }
                }).build();

        Meeting postedMeeting = (Meeting) appointmentProxyService.saveAppointment(meeting);

        assertNotNull(postedMeeting.getId());
        assertEquals(1, postedMeeting.getAttendees().size());

        meetingId = postedMeeting.getId();

        Reminder reminder = new Reminder.Builder().withName("Reminder1").withDay(dfDay.format(appointmentDate))
                .withTime(dfTime.format(appointmentDate))
                .withConfermation(true)
                .withAddress(address).build();

        Reminder postedReminder = (Reminder) appointmentProxyService.saveAppointment(reminder);

        assertNotNull(postedReminder.getId());

        List<Appointment> appointments = appointmentProxyService.findAppointments();

        assertEquals(2, appointments.size());
    }

    @Test
    @Order(4)
    void should_find_appointments_of_attendee_by_day()
    {
        List<Appointment> appointments = appointmentProxyService.findAppointmentsofAttendeeByDay(dfDay.format(appointmentDate), attendeeId);

        assertEquals(1, appointments.size());

    }

    @Test
    @Order(5)
    void should_find_appointments_by_day()
    {
        List<Appointment> appointments = appointmentProxyService.findAppointmentsByDay(dfDay.format(appointmentDate));

        assertEquals(2, appointments.size());

    }

    @Test
    @Order(6)
    void should_update_meeting()
    {

        Meeting meeting = (Meeting) appointmentProxyService.findAppointmentById(meetingId, AppointmentType.MEETING);

        LocalDateTime localDateTime = appointmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(1);
        Date datePlusOne = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        String day = dfDay.format(datePlusOne);
        String time = dfTime.format(datePlusOne);

        meeting.setDay(day);
        meeting.setTime(time);

        Meeting updatedMeeting = (Meeting) appointmentProxyService.saveAppointment(meeting);

        assertEquals(day, updatedMeeting.getDay());
        assertEquals(meetingId, updatedMeeting.getId());

    }

    @Test
    @Order(7)
    void should_find_appointments_by_day_1()
    {
        List<Appointment> appointments = appointmentProxyService.findAppointmentsByDay(dfDay.format(appointmentDate));

        assertEquals(1, appointments.size());

    }
}
