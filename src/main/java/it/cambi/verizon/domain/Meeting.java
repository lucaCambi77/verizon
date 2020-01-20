/**
 * 
 */
package it.cambi.verizon.domain;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * @author luca
 *
 */
@Document
public class Meeting extends Appointment
{

    private Set<String> attendees;
    private int recurrentIntervalDays;

    public Meeting()
    {
        super(AppointmentType.MEETING);
    }

    public Set<String> getAttendees()
    {
        return attendees;
    }

    public void setAttendees(Set<String> attendees)
    {
        this.attendees = attendees;
    }

    public int getRecurrentIntervalDays()
    {
        return recurrentIntervalDays;
    }

    public void setRecurrentIntervalDays(int recurrentIntervalDays)
    {
        this.recurrentIntervalDays = recurrentIntervalDays;
    }

    @JsonIgnoreType
    public static class Builder
    {
        private String name;
        private String day;
        private String time;
        private Address address;
        private boolean isOneOff;
        private Set<String> attendees;
        private boolean confirmed;
        private int recurrentIntervalDays;

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

        public Builder withAttendees(Set<String> attendees)
        {
            this.attendees = attendees;
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

        public Builder withRecurrentInterval(int days)
        {
            this.recurrentIntervalDays = days;
            return this;
        }

        public Meeting build()
        {

            Meeting meeting = new Meeting();

            meeting.setAddress(this.address);
            meeting.setDay(this.day);
            meeting.setName(this.name);
            meeting.setOneOff(this.isOneOff);
            meeting.setTime(this.time);
            meeting.setConfirmed(this.confirmed);
            meeting.attendees = this.attendees;
            meeting.recurrentIntervalDays = this.recurrentIntervalDays;

            return meeting;
        }
    }

}
