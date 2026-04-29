package io.github.wielkikuba.personalfinance.user;

import io.github.wielkikuba.personalfinance.house.HouseService;
import io.github.wielkikuba.personalfinance.user.dto.CreateUserRequest;
import io.github.wielkikuba.personalfinance.user.dto.HouseOperationRequest;
import io.github.wielkikuba.personalfinance.user.dto.UpdateUserRequest;
import io.github.wielkikuba.personalfinance.user.dto.UserDataResponse;
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
    public ResponseEntity<List<UserDataResponse>> getAllUsers(){
        List<User> userList = userService.getUserList();
        return ResponseEntity.ok(userService.userToDtoConverter(userList));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDataResponse> modifyUser(@RequestBody UpdateUserRequest userRequest, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.userToDtoConverter(userService.modifyUser(id,userRequest.getName(),userRequest.getSurname(),userRequest.getHouseId())));
    }

    @PostMapping("/assign")
    public ResponseEntity<UserDataResponse> assignUserToHouse(@RequestBody HouseOperationRequest houseRequest){
        return ResponseEntity.ok(userService.userToDtoConverter(userService.modifyHouseAssignment(houseRequest.getUserId(),houseRequest.getHouseId(),houseRequest.getUserToModifyId(),true)));
    }

    @PostMapping("/remove")
    public ResponseEntity<UserDataResponse> divestUserFromHouse(@RequestBody HouseOperationRequest houseRequest){
        return ResponseEntity.ok(userService.userToDtoConverter(userService.modifyHouseAssignment(houseRequest.getUserId(),houseRequest.getHouseId(),houseRequest.getUserToModifyId(),false)));
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<UserDataResponse>> getUserListByHouse(@PathVariable("houseId") Long houseId){
        List<User> userList = userService.getUserListByHouse(houseService.getHouseById(houseId));
        return ResponseEntity.ok(userService.userToDtoConverter(userList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataResponse> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.userToDtoConverter(userService.getUserById(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDataResponse> getUserByName(@PathVariable("name") String name){
        return ResponseEntity.ok(userService.userToDtoConverter(userService.getUserByName(name)));
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<UserDataResponse> getUserBySurname(@PathVariable("surname") String surname){
        return ResponseEntity.ok(userService.userToDtoConverter(userService.getUserBySurname(surname)));
    }

    @PostMapping
    public ResponseEntity<UserDataResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        User user = userService.createUser(createUserRequest.getName(),createUserRequest.getSurname());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.userToDtoConverter(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}