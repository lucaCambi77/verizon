/**
 * 
 */
package it.cambi.verizon.domain;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author luca
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Meeting.class, name = "MEETING"),
        @JsonSubTypes.Type(value = Reminder.class, name = "REMINDER")
})
public abstract class Appointment
{
    private ObjectId _id;
    private String name;
    private String day;
    private String time;
    private Address address;
    private boolean isOneOff;
    private boolean confirmed;
    private AppointmentType type;

    public Appointment(AppointmentType type)
    {
        this.type = type;
    }

    public Appointment(AppointmentType type, boolean isOneOff)
    {
        this.type = type;
        this.isOneOff = isOneOff;
    }

    public String get_id()
    {
        return null == _id ? null : _id.toString();

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;

    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;

    }

    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;

    }

    public boolean isOneOff()
    {
        return isOneOff;
    }

    public void setOneOff(boolean isOneOff)
    {
        this.isOneOff = isOneOff;
    }

    public AppointmentType getType()
    {
        return type;
    }

    public void setType(AppointmentType type)
    {
        this.type = type;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;

    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed)
    {
        this.confirmed = confirmed;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_id == null) ? 0 : _id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Appointment other = (Appointment) obj;
        if (_id == null)
        {
            if (other._id != null)
                return false;
        }
        else if (!_id.equals(other._id))
            return false;
        return true;
    }

}
