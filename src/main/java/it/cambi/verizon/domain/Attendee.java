/**
 * 
 */
package it.cambi.verizon.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * @author luca
 *
 */
@Document
public class Attendee extends Person
{

    private ObjectId _id;
    private boolean enabled;

    public String get_id()
    {
        return null == _id ? null : _id.toString();

    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @JsonIgnoreType
    public static class Builder
    {
        private String name;
        private String surname;
        private ObjectId _id;
        private Address address;
        private String email;

        public Builder withEmail(String email)
        {
            this.email = email;
            return this;
        }

        public Builder withName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname)
        {
            this.surname = surname;
            return this;
        }

        public Builder withObjectId(ObjectId _id)
        {
            this._id = _id;
            return this;
        }

        public Builder withAddress(Address address)
        {
            this.address = address;
            return this;
        }

        public Attendee build()
        {

            Attendee attendee = new Attendee();
            attendee._id = this._id;
            attendee.setAddress(this.address);
            attendee.setName(this.name);
            attendee.setSurname(this.surname);
            attendee.setEmail(this.email);

            return attendee;
        }
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
        Attendee other = (Attendee) obj;
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
