package io.github.wielkikuba.personalfinance.house.dto;

import io.github.wielkikuba.personalfinance.house.House;
import io.github.wielkikuba.personalfinance.user.dto.UserDataResponse;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class HouseDataResponse {

    public static HouseDataResponse from(House house){
        return new HouseDataResponse(house.getId(),house.getStreet(),house.getNumber(),UserDataResponse.from(house.getOwner()));
    }

    public static List<HouseDataResponse> from(List<House> houseList){
        List<HouseDataResponse> houseDataResponseList = new ArrayList<>();
        for(House house: houseList){
            houseDataResponseList.add(HouseDataResponse.from(house));
        }
        return houseDataResponseList;
    }
    
    private Long id;
    private String street;
    private String number;
    private UserDataResponse owner;
}
