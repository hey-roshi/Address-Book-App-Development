package com.development.Address.Book.App.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Name is required") // Name cannot be empty
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces")
    private String name;
    private String address;
    private int pincode;
    private boolean isPermanentAddress;
}