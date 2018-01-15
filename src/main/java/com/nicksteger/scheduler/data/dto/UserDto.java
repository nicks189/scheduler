package com.nicksteger.scheduler.data.dto;

import com.nicksteger.scheduler.annotations.validation.PasswordMatches;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserDto {
    @NotBlank
    @Size(min = 1, max = 64)
    private String firstName;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 64)
    private String lastName;

    @NotEmpty
    @NotBlank
    @Size(min = 6, max = 32)
    private String password;
    private String matchingPassword;

    @NotEmpty
    @NotBlank
    @Size(min = 4, max = 32)
    private String username;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
