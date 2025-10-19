package pl.jtrend.firecomp.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jtrend.firecomp.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.jtrend.firecomp.dto.FireTruckDto;
import pl.jtrend.firecomp.entity.FireTruck;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.EventRepository;
import pl.jtrend.firecomp.repository.FireStationRepository;
import pl.jtrend.firecomp.repository.FireTruckRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FireTruckService {

    private final FireTruckRepository fireTruckRepository;
    private final FireStationRepository fireStationRepository;
    private final EventRepository eventRepository;
    private final FireCompMapper fireCompMapper;

    // CREATE
    public FireTruckDto create(FireTruckDto dto) {
        FireTruck truck = fireCompMapper.toFireTruck(dto);

        return getFireTruckDto(dto, truck);
    }

    private FireTruckDto getFireTruckDto(FireTruckDto dto, FireTruck truck) {
        if (dto.getStationId() != null) {
            FireStation station = fireStationRepository.findById(dto.getStationId())
                    .orElseThrow(() -> new EntityNotFoundException("Station not found with id " + dto.getStationId()));
            truck.setStation(station);
        }

        FireTruck saved = fireTruckRepository.save(truck);
        return fireCompMapper.toFireTruckDto(saved);
    }

    // READ
    public FireTruckDto findById(Long id) {
        return fireTruckRepository.findById(id)
                .map(fireCompMapper::toFireTruckDto)
                .orElseThrow(() -> new EntityNotFoundException("FireTruck not found with id " + id));
    }

    public Page<FireTruckDto> findAll(Pageable pageable) {
        return fireTruckRepository.findAll(pageable).map(fireCompMapper::toFireTruckDto);
    }

    // UPDATE
    public FireTruckDto update(Long id, FireTruckDto dto) {
        FireTruck existing = fireTruckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FireTruck not found with id " + id));

        existing.setSideNumber(dto.getSideNumber());
        existing.setPlateNumber(dto.getPlateNumber());
        existing.setModel(dto.getModel());
        existing.setYearOfProduction(dto.getYearOfProduction());
        existing.setCapacityLiters(dto.getCapacityLiters());
        existing.setType(dto.getType());
        existing.setTypeDescription(dto.getTypeDescription());
        existing.setRemarks(dto.getRemarks());

        return getFireTruckDto(dto, existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!fireTruckRepository.existsById(id)) {
            throw new EntityNotFoundException("FireTruck not found with id " + id);
        }
        fireTruckRepository.deleteById(id);
    }
}
