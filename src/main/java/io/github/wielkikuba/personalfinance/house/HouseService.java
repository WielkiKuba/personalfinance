package io.github.wielkikuba.personalfinance.house;

import io.github.wielkikuba.personalfinance.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    @Transactional
    public House createHouse(String street, String number, User owner) {
        House house = House.builder()
                .street(street)
                .number(number)
                .owner(owner)
                .build();
        houseRepository.save(house);
        owner.setHouse(house);
        return house;
    }

    @Transactional
    public void deleteHouse(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("House " + id + " does not exist"));
        try {
            houseRepository.delete(house);
            houseRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("House cannot be deleted due to existing constraints.");
        }
    }

    @Transactional(readOnly = true)
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public House getHouseById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("House " + id + " does not exist"));
    }

    @Transactional(readOnly = true)
    public List<House> getHousesByStreet(String street) {
        return houseRepository.findByStreet(street);
    }

    @Transactional(readOnly = true)
    public House getHouseByStreetAndNumber(String street, String number) {
        return houseRepository.findByStreetAndNumber(street, number).orElseThrow(() -> new NoSuchElementException("House at " + street + " " + number + " does not exist"));
    }

    @Transactional(readOnly = true)
    public House getHouseByOwner(User owner) {
        return houseRepository.findByOwner(owner).orElseThrow(() -> new NoSuchElementException("This user does not own any house."));
    }
}