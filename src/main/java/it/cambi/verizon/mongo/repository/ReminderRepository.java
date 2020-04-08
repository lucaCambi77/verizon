/**
 *
 */
package it.cambi.verizon.mongo.repository;

import it.cambi.verizon.domain.Reminder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author luca
 *
 */
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    Reminder findOneById(ObjectId id);

    @Query("{ 'day' : ?0, 'confirmed' : ?1 }")
    List<Reminder> findAllByDay(String day, boolean confirmed);

}
