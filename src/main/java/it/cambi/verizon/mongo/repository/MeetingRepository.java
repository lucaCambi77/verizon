/** */
package it.cambi.verizon.mongo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.verizon.domain.Meeting;

/** @author luca */
public interface MeetingRepository extends MongoRepository<Meeting, String> {

  Meeting findOneById(ObjectId id);

  List<Meeting> findByDay(String day);

  List<Meeting> findByAttendeesInAndDay(List<String> attendees, String day);
}
