package pl.jtrend.firecomp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fire_station")
@Data                      // Generates getters, setters, equals, hashCode, toString
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private LocalDate establishedDate;

    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "fireStation",  orphanRemoval = true)
    private Set<Fireman> firemen = new HashSet<>();
}
