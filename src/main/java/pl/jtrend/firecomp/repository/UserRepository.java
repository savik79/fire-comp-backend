package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jtrend.firecomp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
