/**
 *
 */
package it.cambi.verizon.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/** @author luca */
@EqualsAndHashCode(callSuper = true)
@Document
@Data
@SuperBuilder
public class Meeting extends Appointment {

    private Set<String> attendees;
    private int recurrentIntervalDays;

    public Meeting() {
    }
}
