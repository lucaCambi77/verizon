/**
 * 
 */
package it.cambi.verizon.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.verizon.domain.Attendee;

/**
 * @author luca
 *
 */
public interface AttendeeRepository extends MongoRepository<Attendee, String>
{
    Attendee findOneById(ObjectId id);

}
