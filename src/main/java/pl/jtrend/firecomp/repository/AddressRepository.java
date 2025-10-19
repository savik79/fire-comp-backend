package pl.jtrend.firecomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jtrend.firecomp.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
