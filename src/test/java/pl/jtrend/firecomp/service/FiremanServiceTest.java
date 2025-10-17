package pl.jtrend.firecomp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.FiremanDto;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.entity.Fireman;
import pl.jtrend.firecomp.repository.FireStationRepository;
import pl.jtrend.firecomp.repository.FiremanRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // uses src/test/resources/application-test.yml
@Transactional // rollback DB changes after each test
public class FiremanServiceTest {

    @Autowired
    private FiremanService firemanService;

    @Autowired
    private FiremanRepository firemanRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    private FireStation station;

    @BeforeEach
    void setup() {
        // Prepare one FireStation to link with Fireman
        station = new FireStation();
        station.setName("Central Station");
        fireStationRepository.save(station);
    }

    @Test
    @DisplayName("should create Fireman and persist in database")
    void createFireman() {
        FiremanDto dto = FiremanDto.builder()
                .name("John")
                .surname("Doe")
                .idNumber("FD123")
                .bankAccount("123456789")
                .fireStationId(station.getId())
                .build();

        FiremanDto saved = firemanService.create(dto);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("John");

        // Verify in DB
        Fireman entity = firemanRepository.findById(saved.getId()).orElseThrow();
        assertThat(entity.getFireStation().getId()).isEqualTo(station.getId());
    }

    @Test
    @DisplayName("should get all Firemen as paginated list")
    void getAllFiremen() {
        Fireman f1 = new Fireman();
        f1.setName("John");
        f1.setSurname("Doe");
        firemanRepository.save(f1);

        Fireman f2 = new Fireman();
        f2.setName("Jane");
        f2.setSurname("Smith");
        firemanRepository.save(f2);

        Page<FiremanDto> result = firemanService.getAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(FiremanDto::getName)
                .containsExactlyInAnyOrder("John", "Jane");
    }

    @Test
    @DisplayName("should update Fireman data")
    void updateFireman() {
        Fireman fireman = new Fireman();
        fireman.setName("Old");
        fireman.setSurname("Name");
        fireman.setFireStation(station);
        firemanRepository.save(fireman);

        FiremanDto dto = FiremanDto.builder()
                .name("NewName")
                .surname("Updated")
                .idNumber("FD999")
                .bankAccount("987654321")
                .fireStationId(station.getId())
                .build();

        FiremanDto updated = firemanService.update(fireman.getId(), dto);

        assertThat(updated.getName()).isEqualTo("NewName");
        assertThat(updated.getSurname()).isEqualTo("Updated");

        Fireman inDb = firemanRepository.findById(fireman.getId()).orElseThrow();
        assertThat(inDb.getName()).isEqualTo("NewName");
    }

    @Test
    @DisplayName("should delete Fireman by ID")
    void deleteFireman() {
        Fireman fireman = new Fireman();
        fireman.setName("Temp");
        fireman.setSurname("Temp2");
        firemanRepository.save(fireman);

        Long id = fireman.getId();
        assertThat(firemanRepository.existsById(id)).isTrue();

        firemanService.delete(id);

        assertThat(firemanRepository.existsById(id)).isFalse();
    }

    @Test
    @DisplayName("should throw when Fireman not found for delete")
    void deleteFireman_notFound() {
        assertThatThrownBy(() -> firemanService.delete(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fireman not found");
    }
}
