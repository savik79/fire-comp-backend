package pl.jtrend.firecomp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jtrend.firecomp.dto.FiremanDto;
import pl.jtrend.firecomp.service.FiremanService;

@RestController
@RequestMapping("/firemen")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FiremanController {

    private final FiremanService firemanService;

    // === GET ALL FIREMEN ===
    @GetMapping
    public Page<FiremanDto> getAll(Pageable pageable) {
        return firemanService.getAll(pageable);
    }

    // === GET FIREMAN BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<FiremanDto> getById(@PathVariable Long id) {
        FiremanDto dto = firemanService.getById(id);
        return ResponseEntity.ok(dto);
    }

    // === CREATE FIREMAN ===
    @PostMapping
    public ResponseEntity<FiremanDto> create(@RequestBody FiremanDto dto) {
        FiremanDto created = firemanService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // === UPDATE FIREMAN ===
    @PutMapping("/{id}")
    public ResponseEntity<FiremanDto> update(@PathVariable Long id, @RequestBody FiremanDto dto) {
        FiremanDto updated = firemanService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // === DELETE FIREMAN ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        firemanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
