package pl.jtrend.firecomp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.jtrend.firecomp.dto.EventDto;
import pl.jtrend.firecomp.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@Import(EventControllerTest.TestConfig.class)
@ActiveProfiles("test")
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventService eventService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        EventService eventService() {
            return Mockito.mock(EventService.class);
        }
    }

    private EventDto.EventDtoBuilder baseEventBuilder() {
        return EventDto.builder()
                .name("Fire Drill")
                .description("Routine training exercise")
                .place("Station 12")
                .date(LocalDate.of(2025, 10, 17))
                .durationHours(3.5)
                .mileage(42)
                .fireUnits(3)
                .participantIds(Set.of(10L, 11L))
                .fireTrucksIds(Set.of(5L, 6L));
    }

    @Test
    @DisplayName("GET /events - should return paged list of events")
    void getAllEvents() throws Exception {
        EventDto e1 = baseEventBuilder().id(1L).name("Drill A").build();
        EventDto e2 = baseEventBuilder().id(2L).name("Drill B").build();
        Page<EventDto> page = new PageImpl<>(List.of(e1, e2), PageRequest.of(0, 2), 2);

        Mockito.when(eventService.getAll(any())).thenReturn(page);

        mockMvc.perform(get("/events")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Drill A"))
                .andExpect(jsonPath("$.content[1].name").value("Drill B"));
    }

    @Test
    @DisplayName("GET /events/{id} - should return event by ID")
    void getEventById() throws Exception {
        EventDto event = baseEventBuilder().id(1L).name("Fire Simulation").build();
        Mockito.when(eventService.getById(1L)).thenReturn(event);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fire Simulation"))
                .andExpect(jsonPath("$.place").value("Station 12"));
    }

    @Test
    @DisplayName("POST /events - should create new event")
    void createEvent() throws Exception {
        EventDto request = baseEventBuilder().build();
        EventDto saved = baseEventBuilder().id(1L).build();

        Mockito.when(eventService.create(any(EventDto.class))).thenReturn(saved);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fire Drill"));
    }

    @Test
    @DisplayName("PUT /events/{id} - should update event")
    void updateEvent() throws Exception {
        EventDto request = baseEventBuilder().name("Updated Drill").build();
        EventDto updated = baseEventBuilder().id(1L).name("Updated Drill").build();

        Mockito.when(eventService.update(eq(1L), any(EventDto.class))).thenReturn(updated);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Drill"));
    }

    @Test
    @DisplayName("DELETE /events/{id} - should delete event")
    void deleteEvent() throws Exception {
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isOk());

        Mockito.verify(eventService).delete(1L);
    }
}
