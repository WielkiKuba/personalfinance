package io.github.wielkikuba.personalfinance.house;

import io.github.wielkikuba.personalfinance.house.dto.CreateHouseRequest;
import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<House> createHouse(@RequestBody CreateHouseRequest houseTransferObject){
        House house =  houseService.createHouse(houseTransferObject.getStreet(),houseTransferObject.getNumber(),userService.getUserById(houseTransferObject.getOwner_id()));
        return ResponseEntity.status(HttpStatus.CREATED).body(house);
    }

    @DeleteMapping("/{houseId}")
    public ResponseEntity<Void> deleteHouseById(@PathVariable("houseId") Long houseId, @RequestHeader("X-Session-User-Id") Long sessionUserId){
        houseService.deleteHouse(houseId, sessionUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable("id") Long id){
        return ResponseEntity.ok(houseService.getHouseById(id));
    }

    @GetMapping("/street/{street}")
    public ResponseEntity<List<House>> getHouseByStreet(@PathVariable("street") String street){
        return ResponseEntity.ok(houseService.getHousesByStreet(street));
    }

    @GetMapping("/streetAndNumber/{street}/{number}")
    public ResponseEntity<House> getHouseByStreetAndNumber(@PathVariable("street") String street, @PathVariable("number") String number){
        return ResponseEntity.ok(houseService.getHouseByStreetAndNumber(street,number));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<House> getHouseByOwner(@PathVariable("ownerId") Long ownerId){
        User owner = userService.getUserById(ownerId);
        return ResponseEntity.ok(houseService.getHouseByOwner(owner));
    }
}