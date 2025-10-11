package pl.jtrend.firecomp.dto;

import lombok.Data;

@Data
public class FireStationDto {
    private Long id;
    private String name;
    private String stationNumber;
    private String phoneNumber;
    private String email;
    private String commanderName;
    private Boolean active;
}
