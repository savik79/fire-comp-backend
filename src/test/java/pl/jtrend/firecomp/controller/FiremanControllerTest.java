package pl.jtrend.firecomp.controller;

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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.jtrend.firecomp.dto.FiremanDto;
import pl.jtrend.firecomp.service.FiremanService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FiremanController.class)
@ActiveProfiles("test")
@Import(FiremanControllerTest.MockConfig.class)
public class FiremanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FiremanService firemanService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        FiremanService firemanService() {
            return Mockito.mock(FiremanService.class);
        }
    }


    private FiremanDto buildDto() {
        return FiremanDto.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .idNumber("FD123")
                .bankAccount("123456789")
                .fireStationId(10L)
                .build();
    }

    @Test
    @DisplayName("GET /firemen should return paginated list of Firemen")
    void getAll() throws Exception {
        FiremanDto dto = buildDto();
        Page<FiremanDto> page = new PageImpl<>(List.of(dto));

        when(firemanService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/firemen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John"))
                .andExpect(jsonPath("$.content[0].fireStationId").value(10));

        verify(firemanService).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /firemen/{id} should return single FiremanDto")
    void getById() throws Exception {
        FiremanDto dto = buildDto();
        when(firemanService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/firemen/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    @DisplayName("POST /firemen should create new Fireman and return 201")
    void create() throws Exception {
        FiremanDto dto = buildDto();
        when(firemanService.create(any(FiremanDto.class))).thenReturn(dto);

        String json = """
            {
              "name": "John",
              "surname": "Doe",
              "idNumber": "FD123",
              "bankAccount": "123456789",
              "fireStationId": 10
            }
        """;

        mockMvc.perform(post("/firemen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"));

        verify(firemanService).create(any(FiremanDto.class));
    }

    @Test
    @DisplayName("PUT /firemen/{id} should update and return FiremanDto")
    void update() throws Exception {
        FiremanDto dto = buildDto();
        when(firemanService.update(eq(1L), any(FiremanDto.class))).thenReturn(dto);

        String json = """
            {
              "name": "John",
              "surname": "Doe",
              "idNumber": "FD123",
              "bankAccount": "123456789",
              "fireStationId": 10
            }
        """;

        mockMvc.perform(put("/firemen/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    @DisplayName("DELETE /firemen/{id} should delete and return 204")
    void deleteFireman() throws Exception {
        doNothing().when(firemanService).delete(1L);

        mockMvc.perform(delete("/firemen/1"))
                .andExpect(status().isNoContent());

        verify(firemanService).delete(1L);
    }
}


