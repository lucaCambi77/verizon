/** */
package it.cambi.verizon.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.Meeting;
import it.cambi.verizon.mongo.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** @author luca */
@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingService implements AppointmentService {

  private final MeetingRepository meetingRepository;

  @Override
  public List<Appointment> findAll() {

    return new ArrayList<>(meetingRepository.findAll());
  }

  @Override
  public Appointment findByObjectId(String _id) {

    return meetingRepository.findOneById(new ObjectId(_id));
  }

  @Override
  public List<Appointment> findOfAttendeeByDay(String day, String attendee) {

    return new ArrayList<>(meetingRepository.findByAttendeesInAndDay(Arrays.asList(attendee), day));
  }

  @Override
  public List<Appointment> findByDay(String date) {

    return new ArrayList<>(meetingRepository.findByDay(date));
  }

  @Override
  public Meeting save(Appointment meeting) {
    log.info("... creating or updating meeting " + meeting.getName());

    return meetingRepository.save((Meeting) meeting);
  }

  @Override
  public void delete(Appointment meeting) {
    log.info("... deleting meeting " + meeting.getId());

    meetingRepository.delete((Meeting) meeting);
  }
}
