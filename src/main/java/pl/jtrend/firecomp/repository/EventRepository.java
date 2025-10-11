package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jtrend.firecomp.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
