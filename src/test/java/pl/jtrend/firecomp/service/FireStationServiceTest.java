package pl.jtrend.firecomp.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.AddressDto;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.entity.Address;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.AddressRepository;
import pl.jtrend.firecomp.repository.FireStationRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FireStationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private FireCompMapper fireCompMapper;

    private Address savedAddress;

    @BeforeEach
    void setUp() {
        savedAddress = new Address();
        savedAddress.setStreet("Main Street");
        savedAddress.setCity("Springfield");
        savedAddress.setPostalCode("12345");
        addressRepository.save(savedAddress);
    }

    @Test
    void create_shouldUseExistingAddressWhenAddressIdProvided() {
        // Given
        FireStationDto dto = new FireStationDto();
        dto.setName("Central Station");
        dto.setStationNumber("FS-001");
        dto.setPhoneNumber("555-1234");
        dto.setEmail("central@firedept.com");
        dto.setCommanderName("John Doe");
        dto.setEstablishedDate(LocalDate.of(2000, 1, 1));
        dto.setActive(true);
        dto.setAddressId(savedAddress.getId());

        // When
        FireStationDto result = fireStationService.create(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Central Station");

        FireStation fireStation = fireStationRepository.findById(result.getId()).orElseThrow();
        assertThat(fireStation.getAddress().getId()).isEqualTo(savedAddress.getId());
    }

    @Test
    void create_shouldCreateNewAddressWhenAddressIdNotProvided() {
        // Given
        FireStationDto dto = new FireStationDto();
        dto.setName("West Station");
        dto.setStationNumber("FS-002");
        dto.setPhoneNumber("555-5678");
        dto.setEmail("west@firedept.com");
        dto.setCommanderName("Jane Smith");
        dto.setStreet("Oak Street");
        dto.setCity("Springfield");
        dto.setPostalCode("67890");
        dto.setActive(true);

        // When
        FireStationDto result = fireStationService.create(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("West Station");

        FireStation fireStation = fireStationRepository.findById(result.getId()).orElseThrow();
        assertThat(fireStation.getAddress()).isNotNull();
        assertThat(fireStation.getAddress().getStreet()).isEqualTo("Oak Street");
    }

    @Test
    void create_shouldThrowWhenAddressIdNotFound() {
        // Given
        FireStationDto dto = new FireStationDto();
        dto.setName("North Station");
        dto.setAddressId(999L); // non-existent address ID

        // Expect
        assertThrows(EntityNotFoundException.class, () -> fireStationService.create(dto));
    }
}
