package com.development.Address.Book.App.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String address;
    private int pincode;
    private boolean isPermanentAddress;
}