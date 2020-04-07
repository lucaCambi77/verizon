/**
 *
 */
package it.cambi.verizon.mongo.repository;

import java.util.List;

import it.cambi.verizon.domain.Meeting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.verizon.domain.Reminder;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author luca
 *
 */
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    Reminder findOneById(ObjectId id);

    @Query("{ 'day' : ?0, 'confirmed' : ?1 }")
    List<Reminder> findAllByDay(String day, boolean confirmed);

}
