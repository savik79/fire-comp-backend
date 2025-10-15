package pl.jtrend.firecomp.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.entity.Event;
import pl.jtrend.firecomp.entity.FireTruck;
import pl.jtrend.firecomp.entity.Fireman;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.EventRepository;
import pl.jtrend.firecomp.repository.FireTruckRepository;
import pl.jtrend.firecomp.repository.FiremanRepository;
import pl.jtrend.firecomp.dto.*;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FiremanRepository firemanRepository;
    private final FireCompMapper mapper;
    private final FireTruckRepository fireTruckRepository;

    // === GET ALL (paginated) ===
    public Page<EventDto> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(mapper::toEventDto);
    }

    // === GET BY ID ===
    public EventDto getById(Long id) {
        return eventRepository.findById(id)
                .map(mapper::toEventDto)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // === CREATE ===
    @Transactional
    public EventDto create(EventDto dto) {
        Event event = mapper.toEvent(dto);

        if (dto.getParticipantIds() != null && !dto.getParticipantIds().isEmpty()) {
            Set<Fireman> participants = new HashSet<>(firemanRepository.findAllById(dto.getParticipantIds()));
            event.setParticipants(participants);
        }
        if (dto.getFireTrucksIds()!=null && !dto.getFireTrucksIds().isEmpty()){
            Set<FireTruck> trucks = new HashSet<>(fireTruckRepository.findAllById(dto.getParticipantIds()));
            event.setFireTrucks(trucks);
        }

        Event saved = eventRepository.save(event);
        return mapper.toEventDto(saved);
    }

    // === UPDATE ===
    @Transactional
    public EventDto update(Long id, EventDto dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setPlace(dto.getPlace());
        event.setDate(dto.getDate());
        event.setDurationHours(dto.getDurationHours());
        event.setStartTimestamp(dto.getStartTimestamp());
        event.setEndTimestamp(dto.getEndTimestamp());
        event.setMileage(dto.getMileage());

        if (dto.getParticipantIds() != null) {
            Set<Fireman> participants = new HashSet<>(firemanRepository.findAllById(dto.getParticipantIds()));
            event.setParticipants(participants);
            event.setFireUnits(participants.size());
        }

        Event updated = eventRepository.save(event);
        return mapper.toEventDto(updated);
    }

    // === DELETE ===
    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        eventRepository.deleteById(id);
    }
}
