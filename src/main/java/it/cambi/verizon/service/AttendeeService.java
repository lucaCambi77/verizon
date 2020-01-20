/**
 * 
 */
package it.cambi.verizon.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.cambi.verizon.domain.Attendee;
import it.cambi.verizon.mongo.repository.AttendeeRepository;

/**
 * @author luca
 *
 */
@Service
public class AttendeeService 
{
    private static final Logger log = LoggerFactory.getLogger(AttendeeService.class);

    private @Autowired AttendeeRepository attendeeRepository;

    public List<Attendee> findAll()
    {

        return attendeeRepository.findAll();
    }

    public Attendee findByObjectId(String _id)
    {
        return attendeeRepository.findOneById(new ObjectId(_id));
    }

    public Attendee save(Attendee attendee)
    {
        log.info("... creating new attendee " + attendee.getEmail());
        return attendeeRepository.save(attendee);
    }

}
