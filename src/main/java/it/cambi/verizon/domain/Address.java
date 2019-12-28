/**
 * 
 */
package it.cambi.verizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * @author luca
 *
 */
public class Address
{

    private String street;
    private String zipCode;
    private String city;
    private String country;

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    @JsonIgnoreType
    public static class Builder
    {
        private String street;
        private String zipCode;
        private String city;
        private String country;

        public Builder withStreet(String street)
        {
            this.street = street;
            return this;
        }

        public Builder withZipCode(String zipCode)
        {
            this.zipCode = zipCode;
            return this;
        }

        public Builder withCity(String city)
        {
            this.city = city;
            return this;
        }

        public Builder withCountry(String country)
        {
            this.country = country;
            return this;
        }

        public Address build()
        {

            Address address = new Address();
            address.city = this.city;
            address.country = this.country;
            address.zipCode = this.zipCode;
            address.street = this.street;

            return address;
        }
    }
}
