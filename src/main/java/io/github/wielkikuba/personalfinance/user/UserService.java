package io.github.wielkikuba.personalfinance.user;

import io.github.wielkikuba.personalfinance.house.House;
import io.github.wielkikuba.personalfinance.house.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HouseService houseService;

    @Transactional
    public User createUser(String name,String surname){
        User user = User.builder().name(name).surname(surname).build();
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        User user = getUserById(id);
        try{
            userRepository.delete(user);
            userRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("User cannot be deleted due to existing constraints.");
        }
    }

    @Transactional
    public boolean userExists(Long id){
        return userRepository.existsById(id);
    }

    @Transactional
    public User modifyHouseAssignment(Long userSessionId,Long houseId,Long userToModifyId,boolean isAdding){
        House house = houseService.getHouseById(houseId);
        User userSession = getUserById(userSessionId);
        User userToAdd = getUserById(userToModifyId);
        if(userSession.getId().equals(house.getOwner().getId())){
            if(isAdding){
                userToAdd.setHouse(house);
            }else{
                userToAdd.setHouse(null);
            }
            return userRepository.save(userToAdd);
        }else{
            throw new SecurityException("No permissions. Only the homeowner can add household members.");
        }
    }

    @Transactional
    public User modifyUser(Long id, String name,String surname,Long houseId){
        User user = getUserById(id);
        if(name!=null){
            user.setName(name);
        }
        if(surname!=null){
            user.setSurname(surname);
        }
        if(houseId!=null){
            House house = houseService.getHouseById(houseId);
            user.setHouse(house);
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUserList(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User "+id+" does not exists"));
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserByName(String name){
        User user = userRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("User "+name+" does not exists"));
        return user;
    }
    @Transactional(readOnly = true)
    public User getUserBySurname(String surname){
        User user = userRepository.findBySurname(surname).orElseThrow(()-> new NoSuchElementException("User "+surname+" does not exists"));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getUserListByHouse(House house){
        return userRepository.findByHouse(house);
    }
}
