package pl.jtrend.firecomp.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "fireman")
@Data                      // Generates getters, setters, equals, hashCode, toString
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
}
