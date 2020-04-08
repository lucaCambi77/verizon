/**
 *
 */
package it.cambi.verizon.service;

import it.cambi.verizon.domain.Attendee;
import it.cambi.verizon.mongo.repository.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luca
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    public List<Attendee> findAll() {

        return attendeeRepository.findAll();
    }

    public Attendee findByObjectId(String _id) {
        return attendeeRepository.findOneById(new ObjectId(_id));
    }

    public Attendee save(Attendee attendee) {
        log.info("... creating new attendee " + attendee.getEmail());
        return attendeeRepository.save(attendee);
    }

}
