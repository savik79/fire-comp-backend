package pl.jtrend.firecomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
