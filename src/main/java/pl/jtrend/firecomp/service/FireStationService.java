package pl.jtrend.firecomp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.entity.Address;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.AddressRepository;
import pl.jtrend.firecomp.repository.FireStationRepository;

@Service
@RequiredArgsConstructor
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final AddressRepository addressRepository;
    private final FireCompMapper fireCompMapper;

    public FireStationDto create(FireStationDto fireStationDto) {
        Address address;
        if (fireStationDto.getAddressId() != null) {
            address = addressRepository.findById(fireStationDto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + fireStationDto.getAddressId()));
        } else {
            address = new Address();
            address.setStreet(fireStationDto.getStreet());
            address.setCity(fireStationDto.getCity());
            address.setPostalCode(fireStationDto.getPostalCode());
            addressRepository.save(address);
        }
        FireStation fireStation = fireCompMapper.toFireStation(fireStationDto);
        fireStation.setAddress(address);

        FireStation saved = fireStationRepository.save(fireStation);

        return fireCompMapper.toFireStationDto(saved);

    }


    public FireStationDto findById(Long id) {
        return fireStationRepository.findById(id).map(fireCompMapper::toFireStationDto).orElseThrow(() -> new RuntimeException("FireStation not found"));
    }


    public Page<FireStationDto> findAll(Pageable pageable) {
        return fireStationRepository.findAll(pageable).map(fireCompMapper::toFireStationDto);
    }


    public FireStationDto update(Long id, FireStationDto updated) {
        FireStation existing = fireStationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("FireStation not found with id " + id));
        Address address =  addressRepository.findById(updated.getAddressId()).orElseThrow(() -> new EntityNotFoundException("Address not found with id " + updated.getAddressId()));
        existing.setName(updated.getName());
        existing.setStationNumber(updated.getStationNumber());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setEmail(updated.getEmail());
        existing.setCommanderName(updated.getCommanderName());
        existing.setEstablishedDate(updated.getEstablishedDate());
        existing.setActive(updated.getActive());
        existing.setAddress(address);
        FireStation saved = fireStationRepository.save(existing);
        return fireCompMapper.toFireStationDto(saved);
    }


    public void deleteById(Long id) {
        if (!fireStationRepository.existsById(id)) {
            throw new EntityNotFoundException("FireStation not found with id " + id);
        }
        fireStationRepository.deleteById(id);
    }

}
