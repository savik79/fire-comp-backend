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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.jtrend.firecomp.dto.ReportDto;
import pl.jtrend.firecomp.service.ReportService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@ActiveProfiles("test")
@Import(ReportControllerTest.MockConfig.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReportService reportService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        ReportService reportService() {
            return Mockito.mock(ReportService.class);
        }
    }

    private ReportDto buildDto(Long id) {
        ReportDto dto = new ReportDto();
        dto.setId(id);
        dto.setType("PDF");
        dto.setContent("Sample content".getBytes());
        dto.setEventId(100L);
        dto.setUserId(200L);
        return dto;
    }

    @Test
    @DisplayName("GET /reports should return paginated list of reports")
    void getAllReports() throws Exception {
        ReportDto r1 = buildDto(1L);
        ReportDto r2 = buildDto(2L);
        r2.setType("WORD");
        Page<ReportDto> page = new PageImpl<>(List.of(r1, r2), PageRequest.of(0, 2), 2);

        Mockito.when(reportService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/reports").param("page", "0").param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].type").value("WORD"));

        verify(reportService).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /reports/{id} should return single report")
    void getReportById() throws Exception {
        ReportDto r1 = buildDto(1L);
        Mockito.when(reportService.findById(1L)).thenReturn(r1);

        mockMvc.perform(get("/reports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("PDF"))
                .andExpect(jsonPath("$.eventId").value(100));

        verify(reportService).findById(1L);
    }

    @Test
    @DisplayName("POST /reports should create report and return 201")
    void createReport() throws Exception {
        ReportDto request = buildDto(null);
        ReportDto created = buildDto(10L);

        Mockito.when(reportService.create(any(ReportDto.class))).thenReturn(created);

        mockMvc.perform(post("/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.type").value("PDF"));

        verify(reportService).create(any(ReportDto.class));
    }

    @Test
    @DisplayName("PUT /reports/{id} should update and return report")
    void updateReport() throws Exception {
        ReportDto request = buildDto(null);
        request.setType("XML");
        ReportDto updated = buildDto(5L);
        updated.setType("XML");

        Mockito.when(reportService.update(eq(5L), any(ReportDto.class))).thenReturn(updated);

        mockMvc.perform(put("/reports/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.type").value("XML"));

        verify(reportService).update(eq(5L), any(ReportDto.class));
    }

    @Test
    @DisplayName("DELETE /reports/{id} should delete and return 204")
    void deleteReport() throws Exception {
        doNothing().when(reportService).deleteById(3L);

        mockMvc.perform(delete("/reports/3"))
                .andExpect(status().isNoContent());

        verify(reportService).deleteById(3L);
    }
}
