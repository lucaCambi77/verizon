/** */
package it.cambi.verizon.domain;

import lombok.*;

/** @author luca */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  String street;
  String zipCode;
  String city;
  String country;
}
