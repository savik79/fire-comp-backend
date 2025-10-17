package pl.jtrend.firecomp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FiremanDto {
    private Long id;
    private String name;
    private String surname;
    private Long fireStationId;
    private String idNumber;
    private String bankAccount;
}
