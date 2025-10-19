package pl.jtrend.firecomp.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.FireTruckDto;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.repository.EventRepository;
import pl.jtrend.firecomp.repository.FireStationRepository;
import pl.jtrend.firecomp.repository.FireTruckRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class FireTruckServiceTest {

    @Autowired
    private FireTruckService fireTruckService;

    @Autowired
    private FireTruckRepository fireTruckRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private EventRepository eventRepository;

    private FireStation savedStation;

    @BeforeEach
    void setUp() {
        // Create a FireStation to link to FireTruck
        savedStation = FireStation.builder()
                .name("Central Station")
                .stationNumber("FS-001")
                .build();

        savedStation = fireStationRepository.save(savedStation);
    }

    @Test
    void createFireTruck_shouldPersistAndReturnDto() {
        FireTruckDto dto = FireTruckDto.builder()
                .sideNumber("FT-01")
                .plateNumber("ABC-123")
                .model("Model-X")
                .yearOfProduction(2015)
                .capacityLiters(3000)
                .type("Pumper")
                .stationId(savedStation.getId())
                .remarks("Ready for deployment")
                .build();

        FireTruckDto saved = fireTruckService.create(dto);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getSideNumber()).isEqualTo("FT-01");
        assertThat(saved.getStationId()).isEqualTo(savedStation.getId());
        assertThat(fireTruckRepository.count()).isEqualTo(1);
    }

    @Test
    void findById_shouldReturnExistingFireTruck() {
        FireTruckDto dto = FireTruckDto.builder()
                .sideNumber("FT-02")
                .plateNumber("XYZ-789")
                .stationId(savedStation.getId())
                .build();

        FireTruckDto saved = fireTruckService.create(dto);
        FireTruckDto found = fireTruckService.findById(saved.getId());

        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getSideNumber()).isEqualTo("FT-02");
    }

    @Test
    void findAll_shouldReturnPaginatedList() {
        // Create multiple FireTrucks
        for (int i = 1; i <= 3; i++) {
            FireTruckDto dto = FireTruckDto.builder()
                    .sideNumber("FT-0" + i)
                    .plateNumber("PLATE-" + i)
                    .stationId(savedStation.getId())
                    .build();
            fireTruckService.create(dto);
        }

        Page<FireTruckDto> page = fireTruckService.findAll(PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    void update_shouldModifyExistingFireTruck() {
        FireTruckDto dto = FireTruckDto.builder()
                .sideNumber("FT-10")
                .plateNumber("OLD-123")
                .stationId(savedStation.getId())
                .build();

        FireTruckDto saved = fireTruckService.create(dto);

        FireTruckDto updated = FireTruckDto.builder()
                .sideNumber("FT-10-Updated")
                .plateNumber("NEW-456")
                .stationId(savedStation.getId())
                .build();

        FireTruckDto result = fireTruckService.update(saved.getId(), updated);

        assertThat(result.getSideNumber()).isEqualTo("FT-10-Updated");
        assertThat(result.getPlateNumber()).isEqualTo("NEW-456");
    }

    @Test
    void deleteById_shouldRemoveFireTruck() {
        FireTruckDto dto = FireTruckDto.builder()
                .sideNumber("FT-Delete")
                .plateNumber("DEL-123")
                .stationId(savedStation.getId())
                .build();

        FireTruckDto saved = fireTruckService.create(dto);
        fireTruckService.deleteById(saved.getId());

        assertThrows(EntityNotFoundException.class, () -> fireTruckService.findById(saved.getId()));
        assertThat(fireTruckRepository.count()).isEqualTo(0);
    }
}
