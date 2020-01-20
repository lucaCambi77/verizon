/**
 * 
 */
package it.cambi.verizon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.cambi.verizon.domain.Address;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Attendee;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.domain.Reminder;

/**
 * @author luca
 *
 */
@SpringBootTest(classes = { VerizonApplication.class, ApplicationConfigurationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles({ "test" })
public class VerizonApplicationRestTest
{
    private @Autowired MongoTemplate mongoTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String attendeeId;
    private static String appointmentId;
    private DateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static Date meetingDate = new Date();
    private DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");

    @Test
    @Order(1)
    public void setUp()
    {

        mongoTemplate.dropCollection(Meeting.class);
        mongoTemplate.dropCollection(Reminder.class);
    }

    @Test
    @Order(2)
    public void meetingGreeting()
    {

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + this.port + "/appointment/test", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    @Order(3)
    public void attendeeGreeting()
    {

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + this.port + "/attendee/test", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    @Order(4)
    public void should_create_attendee() throws JsonProcessingException
    {
        Address address = new Address.Builder().withCity("Pistoia").withCountry("Italy").withStreet("Via via").withZipCode("1000").build();
        Attendee attendee = new Attendee.Builder().withName("Luca").withSurname("Cambi").withAddress(address).withEmail("luca.cambi@xxx.com").build();

        HttpEntity<Attendee> request = new HttpEntity<Attendee>(attendee);

        ResponseEntity<Attendee> entity = restTemplate
                .postForEntity("http://localhost:" + this.port + "/attendee/", request, Attendee.class);

        Attendee posted = entity.getBody();
        attendeeId = posted.getId();

        assertNotNull(attendeeId);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    @Order(5)
    public void should_create_appointement() throws Exception
    {
        Address address = new Address.Builder().withCity("Pistoia").withCountry("Italy").withStreet("Via via via").withZipCode("1001").build();

        @SuppressWarnings("serial")
        Meeting meeting = new Meeting.Builder().withName("Meeting1").withDay(dfDay.format(meetingDate)).withTime(dfTime.format(meetingDate))
                .withAddress(address).withConfermation(true)
                .withAttendees(new HashSet<String>()
                {
                    {
                        add(attendeeId);
                    }
                }).build();

        HttpEntity<Meeting> request = new HttpEntity<Meeting>(meeting);

        ResponseEntity<Meeting> entity = restTemplate
                .postForEntity("http://localhost:" + this.port + "/appointment/", request, Meeting.class);

        appointmentId = entity.getBody().getId();

        assertNotNull(appointmentId);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    @Order(6)
    public void should_find_appointments_of_attendee_by_day() throws Exception
    {

        ResponseEntity<Appointment[]> entity = restTemplate.getForEntity(
                "http://localhost:" + this.port + "/appointment/attendeeByday?day=" + dfDay.format(meetingDate) + "&attendee=" + attendeeId,
                Appointment[].class);

        assertEquals(1, entity.getBody().length);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
