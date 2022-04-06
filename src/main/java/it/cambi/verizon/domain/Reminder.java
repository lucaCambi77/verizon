/** */
package it.cambi.verizon.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** @author luca */
@Document
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Reminder extends Appointment {

  String reminder;
}
