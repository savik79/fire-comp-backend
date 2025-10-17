package pl.jtrend.firecomp.dto;

import lombok.Builder;
import lombok.Data;

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
}
