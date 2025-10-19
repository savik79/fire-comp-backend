package pl.jtrend.firecomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FireStationDto {
    private Long id;
    private String name;
    private String stationNumber;
    private String phoneNumber;
    private String email;
    private String commanderName;
    private Boolean active;
    private LocalDate establishedDate;

    //address part if does not exist
    private Long addressId;
    private String street;
    private String postalCode;
    private String city;
    private String country;

}
