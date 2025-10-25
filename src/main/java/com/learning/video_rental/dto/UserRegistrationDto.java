package com.learning.video_rental.dto;

import com.learning.video_rental.entity.RoleType;



public class UserRegistrationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleType role; // Enum type

    public UserRegistrationDto() {}

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public RoleType getRole() { return role; }
    public void setRole(RoleType role) { this.role = role; }
}

