package pl.jtrend.firecomp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.dto.FiremanDto;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.entity.Fireman;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.FireStationRepository;
import pl.jtrend.firecomp.repository.FiremanRepository;

@Service
@RequiredArgsConstructor
public class FiremanService {

    private final FiremanRepository firemanRepository;
    private final FireStationRepository fireStationRepository;
    private final FireCompMapper fireCompMapper;

    public Page<FiremanDto> getAll(Pageable pageable) {
        return firemanRepository.findAll(pageable).map(fireCompMapper::toFiremanDto);
    }

    // === GET FIREMAN BY ID ===
    public FiremanDto getById(Long id) {
        return firemanRepository.findById(id)
                .map(fireCompMapper::toFiremanDto)
                .orElseThrow(() -> new RuntimeException("Fireman not found"));
    }

    // === CREATE FIREMAN ===
    public FiremanDto create(FiremanDto dto) {
        Fireman fireman = fireCompMapper.toFireman(dto);

        if (dto.getFireStationId() != null) {
            FireStation station = fireStationRepository.findById(dto.getFireStationId())
                    .orElseThrow(() -> new RuntimeException("FireStation not found"));
            fireman.setFireStation(station);
        }

        Fireman saved = firemanRepository.save(fireman);
        return fireCompMapper.toFiremanDto(saved);
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
        return fireCompMapper.toFiremanDto(updated);
    }

    // === DELETE FIREMAN ===
    public void delete(Long id) {
        if (!firemanRepository.existsById(id)) {
            throw new RuntimeException("Fireman not found");
        }
        firemanRepository.deleteById(id);
    }
}
