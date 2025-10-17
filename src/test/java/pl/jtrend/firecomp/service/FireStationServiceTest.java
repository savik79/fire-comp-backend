package pl.jtrend.firecomp.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.FireStationRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FireStationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private FireCompMapper fireCompMapper;

    private FireStation station;

    @BeforeEach
    void setUp() {
        station = new FireStation();
        station.setName("Station Alpha");
        station.setStationNumber("101");
        station.setPhoneNumber("555-123");
        station.setEmail("alpha@firedept.com");
        station.setCommanderName("John Doe");

        station.setEstablishedDate(LocalDate.of(2020, 1, 1));
        station.setActive(true);
    }

    @Test
    @DisplayName("save() should persist a FireStation in the DB")
    void save_shouldPersist() {
        FireStation saved = fireStationService.save(station);

        assertThat(saved.getId()).isNotNull();
        assertThat(fireStationRepository.findById(saved.getId())).isPresent();
    }

    @Test
    @DisplayName("findById() should return FireStation if exists")
    void findById_shouldReturn() {
        FireStation saved = fireStationService.save(station);

        assertThat(fireStationService.findById(saved.getId())).isPresent()
                .get().extracting(FireStation::getName).isEqualTo("Station Alpha");
    }

    @Test
    @DisplayName("findAll() should return paginated DTOs")
    void findAll_shouldReturnPage() {
        fireStationService.save(station);

        Page<FireStationDto> page = fireStationService.findAll(PageRequest.of(0, 10));

        assertThat(page).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Station Alpha");
    }

    @Test
    @DisplayName("update() should modify existing FireStation")
    void update_shouldModify() {
        FireStation saved = fireStationService.save(station);

        FireStation updated = new FireStation();
        updated.setName("Station Beta");
        updated.setStationNumber("102");

        FireStation result = fireStationService.update(saved.getId(), updated);

        assertThat(result.getName()).isEqualTo("Station Beta");
        assertThat(result.getStationNumber()).isEqualTo("102");
    }

    @Test
    @DisplayName("update() should throw EntityNotFoundException if not found")
    void update_shouldThrow() {
        assertThatThrownBy(() -> fireStationService.update(999L, station))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("FireStation not found");
    }

    @Test
    @DisplayName("deleteById() should remove the FireStation")
    void deleteById_shouldRemove() {
        FireStation saved = fireStationService.save(station);

        fireStationService.deleteById(saved.getId());

        assertThat(fireStationRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    @DisplayName("deleteById() should throw if FireStation not found")
    void deleteById_shouldThrow() {
        assertThatThrownBy(() -> fireStationService.deleteById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("FireStation not found");
    }
}
