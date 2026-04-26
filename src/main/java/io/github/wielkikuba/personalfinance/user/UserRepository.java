package io.github.wielkikuba.personalfinance.user;

import io.github.wielkikuba.personalfinance.house.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
    Optional<User> findBySurname(String surname);
    boolean existsByName(String name);
    List<User> findByHouse(House house);
}
