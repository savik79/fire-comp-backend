package pl.jtrend.firecomp.dto;

import lombok.Data;

@Data
public class FireTruckDto {
    private Long id;
    private String sideNumber;
    private String registrationNumber;
    private String model;
    private Integer yearOfProduction;
    private Integer capacityLiters;
    private String type;
    private String typeDescription;
    private Long stationId;
    private String remarks;
}
