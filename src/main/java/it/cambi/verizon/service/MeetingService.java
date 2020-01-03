/**
 * 
 */
package it.cambi.verizon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.mongo.repository.MeetingRepository;

/**
 * @author luca
 *
 */
@Service
public class MeetingService implements AppointmentService
{
    private static final Logger log = LoggerFactory.getLogger(MeetingService.class);

    private @Autowired MeetingRepository meetingRepository;

    @Override
    public List<Appointment> findAll()
    {

        return new ArrayList<Appointment>(meetingRepository.findAll());
    }

    @Override
    public Appointment findByObjectId(String _id)
    {

        return meetingRepository.findOneBy_id(new ObjectId(_id));
    }

    @Override
    public List<Appointment> findOfAttendeeByDay(String day, String attendee)
    {

        return new ArrayList<Appointment>(meetingRepository.findOfAttendeeByDay(new String[] { attendee }, day, true));

    }

    @Override
    public List<Appointment> findByDay(String date)
    {

        return meetingRepository.findAllByDay(date).stream().filter(m -> m.isConfirmed()).collect(Collectors.toList());

    }

    @Override
    public Meeting save(Appointment meeting)
    {
        log.info("... creating or updating meeting " + meeting.getName());

        return meetingRepository.save((Meeting) meeting);
    }

    @Override
    public boolean delete(Appointment meeting)
    {
        log.info("... deleting meeting " + meeting.get_id());

        meeting.setConfirmed(false);

        return !meetingRepository.save((Meeting) meeting).isConfirmed();
    }
}
