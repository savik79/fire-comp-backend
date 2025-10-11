package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jtrend.firecomp.entity.Fireman;

@Repository
public interface FiremanRepository extends JpaRepository<Fireman, Long> {
}
