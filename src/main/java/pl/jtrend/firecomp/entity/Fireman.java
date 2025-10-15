package pl.jtrend.firecomp.entity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

@Entity
@Table(name = "fireman")
@Getter
@Setter
@NoArgsConstructor         // Default constructor
@AllArgsConstructor        // All-args constructor
@Builder                   // Builder pattern for easy creation
public class Fireman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private String idNumber;     // optional

    private String bankAccount;  // optional

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private Set<Event> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fire_station_id")
    private FireStation fireStation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Fireman fireman = (Fireman) o;

        return new EqualsBuilder().append(id, fireman.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
