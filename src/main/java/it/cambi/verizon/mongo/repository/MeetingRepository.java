/**
 * 
 */
package it.cambi.verizon.mongo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.verizon.domain.Meeting;

/**
 * @author luca
 *
 */
public interface MeetingRepository extends MongoRepository<Meeting, String>
{

    Meeting findOneBy_id(ObjectId id);

    List<Meeting> findAllByDay(String day);
}
