package pl.jtrend.firecomp.entity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double durationHours;

    private LocalDateTime startTimestamp;

    private LocalDateTime endTimestamp;
    /**
     * how many km has been driven for that event
     */
    private Integer mileage;

    @ManyToMany
    @JoinTable(
            name = "event_fireman",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "fireman_id")
    )
    private Set<Fireman> participants;

    //is dynamically calculated based of number of fireman->participants
    private Integer fireUnits;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Report> reports;

    @OneToMany(mappedBy = "event", orphanRemoval = true)
    private Set<FireTruck> fireTrucks;

    @PrePersist
    @PreUpdate
    public void calculateFireUnits(){
        fireUnits = fireTrucks!=null ? fireTrucks.size() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return new EqualsBuilder().append(id, event.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
