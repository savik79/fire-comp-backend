package pl.jtrend.firecomp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jtrend.firecomp.dto.ReportDto;
import pl.jtrend.firecomp.service.ReportService;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    // GET all reports
    @GetMapping
    public Page<ReportDto> getAll(Pageable pageable) {
        return reportService.findAll(pageable);
    }

    // GET report by id
    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getById(@PathVariable Long id) {
        ReportDto dto = reportService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // CREATE report
    @PostMapping
    public ResponseEntity<ReportDto> create(@RequestBody ReportDto dto) {
        ReportDto created = reportService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // UPDATE report
    @PutMapping("/{id}")
    public ResponseEntity<ReportDto> update(@PathVariable Long id, @RequestBody ReportDto dto) {
        ReportDto updated = reportService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
