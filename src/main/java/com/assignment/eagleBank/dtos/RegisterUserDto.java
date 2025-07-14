package com.assignment.eagleBank.dtos;

import com.assignment.eagleBank.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Address address;
}
