/** */
package it.cambi.verizon.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/** @author luca */
@Document
@Getter
@Setter
@SuperBuilder
public class Meeting extends Appointment {

  private Set<String> attendees;
  private int recurrentIntervalDays;

  public Meeting() {}
}
