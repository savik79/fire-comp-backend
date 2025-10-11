package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jtrend.firecomp.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
