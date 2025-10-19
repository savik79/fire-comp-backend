package pl.jtrend.firecomp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.service.FireStationService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireStationController.class)
@ActiveProfiles("test")
@Import(FireStationControllerTest.MockConfig.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private ObjectMapper objectMapper;

    private FireStationDto fireStationDto;

    @BeforeEach
    void setUp() {
        fireStationDto = FireStationDto.builder()
                .id(1L)
                .name("Central Station")
                .stationNumber("FS-001")
                .phoneNumber("555-1234")
                .email("central@firedept.com")
                .commanderName("John Doe")
                .establishedDate(LocalDate.of(2020, 1, 1))
                .active(true)
                .street("Main Street")
                .city("Springfield")
                .postalCode("12345")
                .build();
    }

    @Test
    void testCreateFireStation() throws Exception {
        Mockito.when(fireStationService.create(any(FireStationDto.class)))
                .thenReturn(fireStationDto);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fireStationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Central Station"))
                .andExpect(jsonPath("$.stationNumber").value("FS-001"));
    }

    @Test
    void testFindById() throws Exception {
        Mockito.when(fireStationService.findById(1L)).thenReturn(fireStationDto);

        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Central Station"));
    }

    @Test
    void testUpdateFireStation() throws Exception {
        FireStationDto updated = FireStationDto.builder()
                .id(1L)
                .name("Updated Station")
                .stationNumber("FS-002")
                .build();

        Mockito.when(fireStationService.update(eq(1L), any(FireStationDto.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/firestation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Station"))
                .andExpect(jsonPath("$.stationNumber").value("FS-002"));
    }

    @Test
    void testDeleteFireStation() throws Exception {
        Mockito.doNothing().when(fireStationService).deleteById(1L);

        mockMvc.perform(delete("/firestation/1"))
                .andExpect(status().isNoContent());
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        FireStationService fireStationService(){
            return Mockito.mock(FireStationService.class);
        }
    }
}
