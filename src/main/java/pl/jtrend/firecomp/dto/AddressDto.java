package pl.jtrend.firecomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressDto {

    private Long id;

    private String street;
    private String postalCode;
    private String city;
    private String country;
}
