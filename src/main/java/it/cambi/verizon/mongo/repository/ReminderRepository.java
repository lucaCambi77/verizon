/**
 * 
 */
package it.cambi.verizon.mongo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.verizon.domain.Reminder;

/**
 * @author luca
 *
 */
public interface ReminderRepository extends MongoRepository<Reminder, String>
{

    Reminder findOneById(ObjectId id);

    List<Reminder> findAllByDay(String day);
}
