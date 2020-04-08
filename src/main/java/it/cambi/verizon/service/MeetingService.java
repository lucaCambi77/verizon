/**
 *
 */
package it.cambi.verizon.service;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.mongo.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luca
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingService implements AppointmentService {

    private final MeetingRepository meetingRepository;

    @Override
    public List<Appointment> findAll() {

        return new ArrayList<Appointment>(meetingRepository.findAll());
    }

    @Override
    public Appointment findByObjectId(String _id) {

        return meetingRepository.findOneById(new ObjectId(_id));
    }

    @Override
    public List<Appointment> findOfAttendeeByDay(String day, String attendee) {

        return new ArrayList<Appointment>(meetingRepository.findOfAttendeeByDay(new String[]{attendee}, day, true));

    }

    @Override
    public List<Appointment> findByDay(String date) {

        return new ArrayList<Appointment>(meetingRepository.findAllByDay(date, true));

    }

    @Override
    public Meeting save(Appointment meeting) {
        log.info("... creating or updating meeting " + meeting.getName());

        return meetingRepository.save((Meeting) meeting);
    }

    @Override
    public boolean delete(Appointment meeting) {
        log.info("... deleting meeting " + meeting.getId());

        meeting.setConfirmed(false);

        return !meetingRepository.save((Meeting) meeting).isConfirmed();
    }
}
