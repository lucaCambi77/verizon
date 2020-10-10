/** */
package it.cambi.verizon.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/** @author luca */
@Document
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Attendee extends Person {

  private @MongoId ObjectId id;
  private boolean enabled;

  public Attendee() {}

  public String getId() {
    return null == id ? null : id.toString();
  }
}
