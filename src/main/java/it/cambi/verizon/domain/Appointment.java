/** */
package it.cambi.verizon.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** @author luca */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Meeting.class, name = "MEETING"),
  @JsonSubTypes.Type(value = Reminder.class, name = "REMINDER")
})
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
@Data
public class Appointment {

  @MongoId ObjectId id;
  String name;
  String day;
  String time;
  Address address;
  AppointmentType type;
  boolean allDay;
  Repeat repeat = Repeat.DOES_NOT_REPEAT;

  public String getId() {
    return null == id ? null : id.toString();
  }
}
