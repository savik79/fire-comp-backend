package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jtrend.firecomp.entity.FireStation;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
