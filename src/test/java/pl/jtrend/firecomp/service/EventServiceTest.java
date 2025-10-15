package pl.jtrend.firecomp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.EventDto;
import pl.jtrend.firecomp.entity.*;
import pl.jtrend.firecomp.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EventServiceTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private FiremanRepository firemanRepository;
    @Autowired
    private FireTruckRepository fireTruckRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateAndRetrieveEventWithRelations() {
        // === ARRANGE ===

        // --- Create station ---
        FireStation station = fireStationRepository.save(
                FireStation.builder()
                        .name("Central Station")
                        .build()
        );

        // --- Create firemen ---
        Fireman john = firemanRepository.save(Fireman.builder().name("John").surname("Doe").build());
        Fireman anna = firemanRepository.save(Fireman.builder().name("Anna").surname("Smith").build());

        // --- Create fire trucks ---
        FireTruck truck1 = fireTruckRepository.save(FireTruck.builder()
                .sideNumber("FT-01")
                .plateNumber("ABC-123")
                .model("Mercedes ATEGO")
                .yearOfProduction(2018)
                .capacityLiters(3000)
                .station(station)
                .build());

        FireTruck truck2 = fireTruckRepository.save(FireTruck.builder()
                .sideNumber("FT-02")
                .plateNumber("XYZ-789")
                .model("MAN TGM")
                .yearOfProduction(2020)
                .capacityLiters(4000)
                .station(station)
                .build());

        // --- Create user (for report author) ---
        User officer = userRepository.save(User.builder()
                .username("commander")
                .password("secret")
                .build());

        // --- Create event DTO ---
        EventDto dto = new EventDto();
        dto.setName("Warehouse Fire");
        dto.setPlace("Sector 7");
        dto.setDate(LocalDate.now());
        dto.setDurationHours(3.5);
        dto.setStartTimestamp(LocalDateTime.now().minusHours(4));
        dto.setEndTimestamp(LocalDateTime.now());
        dto.setParticipantIds(Set.of(john.getId(), anna.getId()));
        dto.setFireTrucksIds(Set.of(truck1.getId(), truck2.getId()));

        // === ACT ===
        EventDto savedEventDto = eventService.create(dto);

        // === ASSERT ===
        assertThat(savedEventDto.getId()).isNotNull();
        assertThat(savedEventDto.getFireUnits()).isEqualTo(2);

        // --- Retrieve persisted event ---
        Event savedEvent = eventRepository.findById(savedEventDto.getId()).orElseThrow();

        // --- Create and associate reports ---
        Report report = Report.builder()
                .type(ReportType.PDF)
                .content("Example PDF report".getBytes())
                .event(savedEvent)
                .user(officer)
                .build();

        reportRepository.save(report);


        fireTruckRepository.saveAll(List.of(truck1, truck2));

        // --- Persist final event with relations ---
        savedEvent.setFireTrucks(new HashSet<>(Set.of(truck1, truck2)));
        savedEvent.setReports(new HashSet<>(Set.of(report)));
        eventRepository.save(savedEvent);

        // === ASSERT (verify relationships) ===
        Event reloaded = eventRepository.findById(savedEvent.getId()).orElseThrow();

        assertThat(reloaded.getReports()).hasSize(1);
        assertThat(reloaded.getReports().iterator().next().getUser().getUsername())
                .isEqualTo("commander");

        assertThat(reloaded.getFireTrucks()).hasSize(2);
        assertThat(reloaded.getFireTrucks().iterator().next().getStation().getName())
                .isEqualTo("Central Station");
    }


}
