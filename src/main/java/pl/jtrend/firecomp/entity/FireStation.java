package pl.jtrend.firecomp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fire_station")
@Getter
@Setter
@NoArgsConstructor         // Default constructor
@AllArgsConstructor        // All-args constructor
@Builder
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String stationNumber;

    private String phoneNumber;
    private String email;
    private String commanderName;

    @OneToOne()
    @JoinColumn(name = "address_id")
    private Address address;

    private LocalDate establishedDate;

    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "fireStation",  orphanRemoval = true)
    private Set<Fireman> firemen = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FireStation that = (FireStation) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
