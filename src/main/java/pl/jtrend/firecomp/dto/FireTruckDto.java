package pl.jtrend.firecomp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FireTruckDto {
    private Long id;
    private String sideNumber;
    private String plateNumber;
    private String model;
    private Integer yearOfProduction;
    private Integer capacityLiters;
    private String type;
    private String typeDescription;
    private Long stationId;
    private String remarks;
}
