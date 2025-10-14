package pl.jtrend.firecomp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EventDto {
    private Long id;
    private String name;
    private String description;
    private String place;
    private LocalDate date;
    private Double durationHours;
    private LocalDateTime startTimestamp;
    private LocalDateTime endTimestamp;
    private Integer mileage;
    private Integer fireUnits;
    private Set<Long> participantIds;
    private Set<Long> fireTrucksIds;
}
