package pl.jtrend.firecomp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fire_truck")
@Data                      // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor         // Default constructor
@AllArgsConstructor        // All-args constructor
@Builder
public class FireTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String sideNumber;

    @Column(nullable = false)
    private String registrationNumber;

    private String model;
    private Integer yearOfProduction;
    private Integer capacityLiters;

    private String type;

    private String typeDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private FireStation station;
    /**
     * notes for the vehicle
     */
    private String remarks;
}
