/**
 *
 */
package it.cambi.verizon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.cambi.verizon.domain.Address;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.domain.Reminder;

/** @author luca */
@SpringBootTest(
        classes = {VerizonApplication.class},
        webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class VerizonApplicationRestTest {
    private @Autowired
    MongoTemplate mongoTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final DateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final Date meetingDate = new Date();
    private final DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");

    @BeforeEach
    public void setUp() {

        mongoTemplate.dropCollection(Meeting.class);
        mongoTemplate.dropCollection(Reminder.class);
    }

    @Test
    public void should_find_meetingByDay() {
        Meeting meeting =
                Meeting.builder()
                        .name("Meeting1")
                        .day(dfDay.format(meetingDate))
                        .time(dfTime.format(meetingDate))
                        .address(
                                Address.builder()
                                        .city("Pistoia")
                                        .country("Italy")
                                        .street("Via via via")
                                        .zipCode("1001")
                                        .build())
                        .attendees(new HashSet<>(Collections.singletonList("pippo")))
                        .type(AppointmentType.MEETING)
                        .build();

        HttpEntity<Meeting> request = new HttpEntity<>(meeting);

        ResponseEntity<Meeting> entity =
                restTemplate.postForEntity(
                        "http://localhost:" + this.port + "/appointment/", request, Meeting.class);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        ResponseEntity<Appointment[]> entity1 =
                restTemplate.getForEntity(
                        "http://localhost:"
                                + this.port
                                + "/appointment/attendeeByDay?day="
                                + dfDay.format(meetingDate)
                                + "&attendee="
                                + "pippo",
                        Appointment[].class);

        assertEquals(1, Arrays.stream(entity1.getBody()).count());
    }
}
