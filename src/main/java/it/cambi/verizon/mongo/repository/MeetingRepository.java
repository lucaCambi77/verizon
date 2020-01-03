/**
 * 
 */
package it.cambi.verizon.mongo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import it.cambi.verizon.domain.Meeting;

/**
 * @author luca
 *
 */
public interface MeetingRepository extends MongoRepository<Meeting, String>
{

    Meeting findOneBy_id(ObjectId id);

    List<Meeting> findAllByDay(String day);

    @Query("{ 'attendees' : { $all : ?0 }, 'day' : ?1, 'confirmed' : ?2 }")
    List<Meeting> findOfAttendeeByDay(String[] attendees, String day, boolean confirmed);
}
