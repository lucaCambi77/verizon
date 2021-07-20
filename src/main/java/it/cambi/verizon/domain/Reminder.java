/**
 *
 */
package it.cambi.verizon.domain;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author luca
 *
 */
@Document
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Reminder extends Appointment
{
    public boolean isOneOff()
    {
        return false;
    }

    public Reminder()
    {
        super(AppointmentType.REMINDER);
    }
}
