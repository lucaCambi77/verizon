/**
 *
 */
package it.cambi.verizon.mongo.repository;

import it.cambi.verizon.domain.Attendee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author luca
 *
 */
public interface AttendeeRepository extends MongoRepository<Attendee, String>
{
    Attendee findOneById(ObjectId id);

}
