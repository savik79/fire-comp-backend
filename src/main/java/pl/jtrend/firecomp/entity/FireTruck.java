package pl.jtrend.firecomp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "fire_truck")
@Getter
@Setter
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
    private String plateNumber;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")  // owning side
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FireTruck fireTruck = (FireTruck) o;

        return new EqualsBuilder().append(id, fireTruck.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
