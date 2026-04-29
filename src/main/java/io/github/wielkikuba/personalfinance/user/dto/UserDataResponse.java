package io.github.wielkikuba.personalfinance.user.dto;

import io.github.wielkikuba.personalfinance.house.House;
import io.github.wielkikuba.personalfinance.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class UserDataResponse {

    public static UserDataResponse from(User user){
        return new UserDataResponse(user.getId(),user.getName(),user.getSurname(),user.getHouse());
    }

    public static List<UserDataResponse> from(List<User> userList){
        List<UserDataResponse> userDataResponseList = new ArrayList<>();
        for(User user:userList){
            userDataResponseList.add(UserDataResponse.from(user));
        }
        return userDataResponseList;
    }

    private Long id;
    private String name;
    private String surname;
    private House house;
}
