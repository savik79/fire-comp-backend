package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jtrend.firecomp.entity.FireTruck;

@Repository
public interface FireTruckRepository extends JpaRepository<FireTruck, Long> {
}
