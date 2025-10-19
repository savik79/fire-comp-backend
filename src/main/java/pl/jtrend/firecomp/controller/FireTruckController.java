package pl.jtrend.firecomp.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jtrend.firecomp.dto.FireTruckDto;
import pl.jtrend.firecomp.service.FireTruckService;

@RestController
@RequestMapping("/firetrucks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FireTruckController {

    private final FireTruckService fireTruckService;

    @PostMapping
    public ResponseEntity<FireTruckDto> create(@RequestBody FireTruckDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fireTruckService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FireTruckDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fireTruckService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FireTruckDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(fireTruckService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FireTruckDto> update(@PathVariable Long id, @RequestBody FireTruckDto dto) {
        return ResponseEntity.ok(fireTruckService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fireTruckService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
