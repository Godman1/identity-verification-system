package com.identity.verification.dto;

import com.identity.verification.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private long id;
    private String phoneNumber;
    private String nin;
    private String firstName;
    private String lastName;

    public UserDto(long id, String phoneNumber, String nin, String firstName, String lastName) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.nin = nin;
        this.firstName = firstName;
        this.lastName = lastName;
    }
//
//    public UserDto(User user) {
////        this.id= user.getId();
//        this.phoneNumber = user.getPhoneNumber();
//        this.nin = user.getNin();
//        this.firstName = user.getFirstName();
//        this.lastName = user.getLastName();
//
//
//    }
//
//    public User toUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        user.setNin(userDto.getNin());
//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        return user;
//    }
}
