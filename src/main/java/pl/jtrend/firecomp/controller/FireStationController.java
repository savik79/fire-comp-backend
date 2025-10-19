package pl.jtrend.firecomp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.service.FireStationService;

@RestController
@RequestMapping("/firestation")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FireStationController {
    private final FireStationService fireStationService;

    @PostMapping
    public ResponseEntity<FireStationDto> create(@RequestBody FireStationDto fireStationDto) {
        FireStationDto saved  = fireStationService.create(fireStationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<FireStationDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(fireStationService.findById(id));
    }

    // READ ALL (with pagination)
    @GetMapping
    public ResponseEntity<Page<FireStationDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(fireStationService.findAll(pageable));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FireStationDto> update(@PathVariable Long id, @RequestBody FireStationDto dto) {
        FireStationDto updated = fireStationService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        fireStationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
