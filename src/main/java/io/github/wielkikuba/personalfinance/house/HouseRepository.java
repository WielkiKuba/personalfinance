package io.github.wielkikuba.personalfinance.house;

import io.github.wielkikuba.personalfinance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House,Long> {
    List<House> findByStreet(String street);
    Optional<House> findByStreetAndNumber(String street,String number);
    Optional<House> findByOwner(User owner);
}
