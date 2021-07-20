/** */
package it.cambi.verizon.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

/** @author luca */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Meeting.class, name = "MEETING"),
  @JsonSubTypes.Type(value = Reminder.class, name = "REMINDER")
})
@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Appointment {
  private @MongoId ObjectId id;
  private String name;
  private String day;
  private String time;
  private Address address;
  private boolean isOneOff;
  private boolean confirmed;
  private AppointmentType type;

  public Appointment(AppointmentType type) {
    this.type = type;
  }

  public String getId() {
    return null == id ? null : id.toString();
  }
}
