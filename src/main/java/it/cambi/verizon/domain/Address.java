/**
 *
 */
package it.cambi.verizon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author luca */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    String street;
    String zipCode;
    String city;
    String country;
}
