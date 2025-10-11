package pl.jtrend.firecomp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jtrend.firecomp.repository.FireStationRepository;

@Service
@RequiredArgsConstructor
public class FireStationService {
    private final FireStationRepository fireStationRepository;

}
