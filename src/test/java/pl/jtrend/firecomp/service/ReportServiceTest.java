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
import pl.jtrend.firecomp.dto.ReportDto;
import pl.jtrend.firecomp.entity.Event;
import pl.jtrend.firecomp.entity.User;
import pl.jtrend.firecomp.repository.EventRepository;
import pl.jtrend.firecomp.repository.ReportRepository;
import pl.jtrend.firecomp.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private Event savedEvent;
    private User savedUser;

    @BeforeEach
    void setUp() {
        // minimal valid Event
        Event event = Event.builder()
                .name("Drill")
                .place("Training Ground")
                .date(LocalDate.now())
                .durationHours(2.0)
                .build();
        savedEvent = eventRepository.save(event);

        // minimal valid User
        User user = User.builder()
                .username("john")
                .password("secret")
                .email("john@example.com")
                .build();
        savedUser = userRepository.save(user);
    }

    @Test
    void createReport_shouldPersistAndReturnDto() {
        ReportDto dto = new ReportDto();
        dto.setType("PDF");
        dto.setContent("Sample content".getBytes());
        dto.setEventId(savedEvent.getId());
        dto.setUserId(savedUser.getId());

        ReportDto saved = reportService.create(dto);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getType()).isEqualTo("PDF");
        assertThat(saved.getEventId()).isEqualTo(savedEvent.getId());
        assertThat(saved.getUserId()).isEqualTo(savedUser.getId());
        assertThat(reportRepository.count()).isEqualTo(1);
    }

    @Test
    void findById_shouldReturnExistingReport() {
        ReportDto dto = new ReportDto();
        dto.setType("WORD");
        dto.setContent("Another".getBytes());
        dto.setEventId(savedEvent.getId());
        dto.setUserId(savedUser.getId());

        ReportDto saved = reportService.create(dto);
        ReportDto found = reportService.findById(saved.getId());

        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getType()).isEqualTo("WORD");
    }

    @Test
    void findAll_shouldReturnPaginatedList() {
        for (int i = 0; i < 3; i++) {
            ReportDto dto = new ReportDto();
            dto.setType("XML");
            dto.setContent(("C" + i).getBytes());
            dto.setEventId(savedEvent.getId());
            dto.setUserId(savedUser.getId());
            reportService.create(dto);
        }

        Page<ReportDto> page = reportService.findAll(PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    void update_shouldModifyExistingReport() {
        ReportDto dto = new ReportDto();
        dto.setType("PDF");
        dto.setContent("Old".getBytes());
        dto.setEventId(savedEvent.getId());
        dto.setUserId(savedUser.getId());
        ReportDto saved = reportService.create(dto);

        ReportDto update = new ReportDto();
        update.setType("WORD");
        update.setContent("New".getBytes());
        update.setEventId(savedEvent.getId());
        update.setUserId(savedUser.getId());

        ReportDto result = reportService.update(saved.getId(), update);

        assertThat(result.getType()).isEqualTo("WORD");
        assertThat(new String(result.getContent())).isEqualTo("New");
    }

    @Test
    void deleteById_shouldRemoveReport() {
        ReportDto dto = new ReportDto();
        dto.setType("XML");
        dto.setContent("ToDelete".getBytes());
        dto.setEventId(savedEvent.getId());
        dto.setUserId(savedUser.getId());
        ReportDto saved = reportService.create(dto);

        reportService.deleteById(saved.getId());

        assertThrows(EntityNotFoundException.class, () -> reportService.findById(saved.getId()));
        assertThat(reportRepository.count()).isEqualTo(0);
    }

    @Test
    void create_withNonExistingEvent_shouldThrow() {
        ReportDto dto = new ReportDto();
        dto.setType("PDF");
        dto.setContent("x".getBytes());
        dto.setEventId(99999L);
        dto.setUserId(savedUser.getId());

        assertThrows(EntityNotFoundException.class, () -> reportService.create(dto));
    }

    @Test
    void update_withNonExistingUser_shouldThrow() {
        ReportDto dto = new ReportDto();
        dto.setType("PDF");
        dto.setContent("x".getBytes());
        dto.setEventId(savedEvent.getId());
        dto.setUserId(savedUser.getId());
        ReportDto saved = reportService.create(dto);

        ReportDto update = new ReportDto();
        update.setType("XML");
        update.setContent("y".getBytes());
        update.setEventId(savedEvent.getId());
        update.setUserId(424242L); // not existing

        assertThrows(EntityNotFoundException.class, () -> reportService.update(saved.getId(), update));
    }
}
