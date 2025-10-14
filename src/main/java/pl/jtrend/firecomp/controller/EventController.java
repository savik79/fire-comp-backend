package pl.jtrend.firecomp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.jtrend.firecomp.dto.EventDto;
import pl.jtrend.firecomp.service.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public Page<EventDto> getAll(Pageable pageable) {
        return eventService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public EventDto getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @PostMapping
    public EventDto create(@RequestBody EventDto dto) {
        return eventService.create(dto);
    }

    @PutMapping("/{id}")
    public EventDto update(@PathVariable Long id, @RequestBody EventDto dto) {
        return eventService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }

}
