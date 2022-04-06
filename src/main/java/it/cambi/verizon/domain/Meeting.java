/** */
package it.cambi.verizon.domain;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** @author luca */
@EqualsAndHashCode(callSuper = true)
@Document
@Data
@NoArgsConstructor
@SuperBuilder
public class Meeting extends Appointment {

  Set<String> attendees;
  String title;
  String description;
}
