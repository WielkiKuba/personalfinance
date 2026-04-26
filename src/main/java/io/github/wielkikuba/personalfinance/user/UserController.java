package io.github.wielkikuba.personalfinance.user;

import io.github.wielkikuba.personalfinance.house.HouseService;
import io.github.wielkikuba.personalfinance.user.dto.CreateUserRequest;
import io.github.wielkikuba.personalfinance.user.dto.HouseOperationRequest;
import io.github.wielkikuba.personalfinance.user.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getUserList();
        return ResponseEntity.ok(userList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> modifyUser(@RequestBody UpdateUserRequest userRequest,@PathVariable Long id){
        return ResponseEntity.ok(userService.modifyUser(id,userRequest.getName(),userRequest.getSurname(),userRequest.getHouseId()));
    }

    @PostMapping("/assign")
    public ResponseEntity<User> assignUserToHouse(@RequestBody HouseOperationRequest houseRequest){
        return ResponseEntity.ok(userService.modifyHouseAssignment(houseRequest.getUserId(),houseRequest.getHouseId(),houseRequest.getUserToModifyId(),true));
    }

    @PostMapping("/remove")
    public ResponseEntity<User> divestUserFromHouse(@RequestBody HouseOperationRequest houseRequest){
        return ResponseEntity.ok(userService.modifyHouseAssignment(houseRequest.getUserId(),houseRequest.getHouseId(),houseRequest.getUserToModifyId(),false));
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<User>> getUserListByHouse(@PathVariable Long houseId){
        List<User> userList = userService.getUserListByHouse(houseService.getHouseById(houseId));
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name){
        return ResponseEntity.ok(userService.getUserByName(name));
    }
    @GetMapping("/surname/{surname}")
    public ResponseEntity<User> getUserBySurname(@PathVariable String surname){
        return ResponseEntity.ok(userService.getUserBySurname(surname));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest){
        User user = userService.createUser(createUserRequest.getName(),createUserRequest.getSurname());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
