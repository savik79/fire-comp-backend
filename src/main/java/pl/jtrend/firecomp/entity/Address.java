package pl.jtrend.firecomp.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String postalCode;
    private String city;
    private String country;
}
