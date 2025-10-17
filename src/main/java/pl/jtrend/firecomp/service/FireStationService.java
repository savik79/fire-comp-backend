package pl.jtrend.firecomp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.FireStationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final FireCompMapper fireCompMapper;

    public FireStation save(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }


    public Optional<FireStation> findById(Long id) {
        return fireStationRepository.findById(id);
    }


    public Page<FireStationDto> findAll(Pageable pageable) {
        return fireStationRepository.findAll(pageable).map(fireCompMapper::toFireStationDto);
    }


    public FireStation update(Long id, FireStation updated) {
        FireStation existing = fireStationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("FireStation not found with id " + id));

        existing.setName(updated.getName());
        existing.setStationNumber(updated.getStationNumber());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setEmail(updated.getEmail());
        existing.setCommanderName(updated.getCommanderName());
        existing.setAddress(updated.getAddress());
        existing.setEstablishedDate(updated.getEstablishedDate());
        existing.setActive(updated.getActive());

        return fireStationRepository.save(existing);
    }


    public void deleteById(Long id) {
        if (!fireStationRepository.existsById(id)) {
            throw new EntityNotFoundException("FireStation not found with id " + id);
        }
        fireStationRepository.deleteById(id);
    }

}
