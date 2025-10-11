package pl.jtrend.firecomp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.FiremanDto;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.entity.Fireman;
import pl.jtrend.firecomp.mapper.Mapper;
import pl.jtrend.firecomp.repository.FireStationRepository;
import pl.jtrend.firecomp.repository.FiremanRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiremanService {

    private final FiremanRepository firemanRepository;
    private final FireStationRepository fireStationRepository;
    private final Mapper mapper;

    // === GET ALL FIREMEN ===
    public List<FiremanDto> getAll() {
        return firemanRepository.findAll()
                .stream()
                .map(mapper::toFiremanDto)
                .toList();
    }

    // === GET FIREMAN BY ID ===
    public FiremanDto getById(Long id) {
        return firemanRepository.findById(id)
                .map(mapper::toFiremanDto)
                .orElseThrow(() -> new RuntimeException("Fireman not found"));
    }

    // === CREATE FIREMAN ===
    public FiremanDto create(FiremanDto dto) {
        Fireman fireman = mapper.toFireman(dto);

        if (dto.getFireStationId() != null) {
            FireStation station = fireStationRepository.findById(dto.getFireStationId())
                    .orElseThrow(() -> new RuntimeException("FireStation not found"));
            fireman.setFireStation(station);
        }

        Fireman saved = firemanRepository.save(fireman);
        return mapper.toFiremanDto(saved);
    }

    // === UPDATE FIREMAN ===
    @Transactional
    public FiremanDto update(Long id, FiremanDto dto) {
        Fireman fireman = firemanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fireman not found"));

        fireman.setName(dto.getName());
        fireman.setSurname(dto.getSurname());
        fireman.setIdNumber(dto.getIdNumber());
        fireman.setBankAccount(dto.getBankAccount());

        if (dto.getFireStationId() != null) {
            FireStation station = fireStationRepository.findById(dto.getFireStationId())
                    .orElseThrow(() -> new RuntimeException("FireStation not found"));
            fireman.setFireStation(station);
        } else {
            fireman.setFireStation(null); // detach from station if null
        }

        Fireman updated = firemanRepository.save(fireman);
        return mapper.toFiremanDto(updated);
    }

    // === DELETE FIREMAN ===
    public void delete(Long id) {
        if (!firemanRepository.existsById(id)) {
            throw new RuntimeException("Fireman not found");
        }
        firemanRepository.deleteById(id);
    }
}
