/**
 * 
 */
package it.cambi.verizon.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * @author luca
 *
 */
@Document
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

    @JsonIgnoreType
    public static class Builder
    {
        private String name;
        private String day;
        private String time;
        private Address address;
        private boolean isOneOff;
        private boolean confirmed;

        public Builder withName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder withDay(String day)
        {
            this.day = day;
            return this;
        }

        public Builder withTime(String time)
        {
            this.time = time;
            return this;
        }

        public Builder withIsOneOff(boolean isOneOff)
        {
            this.isOneOff = isOneOff;
            return this;
        }

        public Builder withAddress(Address address)
        {
            this.address = address;
            return this;
        }

        public Builder withConfermation(boolean confirmed)
        {
            this.confirmed = confirmed;
            return this;
        }

        public Reminder build()
        {

            Reminder reminder = new Reminder();

            reminder.setAddress(this.address);
            reminder.setDay(this.day);
            reminder.setName(this.name);
            reminder.setOneOff(this.isOneOff);
            reminder.setTime(this.time);
            reminder.setConfirmed(this.confirmed);

            return reminder;
        }
    }

}
