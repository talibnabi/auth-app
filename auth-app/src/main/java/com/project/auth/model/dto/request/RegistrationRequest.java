package com.project.auth.model.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.auth.validation.constraints.validator.EmailValidation;
import com.project.auth.validation.constraints.validator.PasswordValidation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Data
public class RegistrationRequest {

    public RegistrationRequest() {
        /*
        Default password
        * */
        this.password = "talibnabi2023";
        this.passwordConfirmation = "talibnabi2023";
    }

    @NotNull
    private String username;

    @NotNull
    @EmailValidation
    private String email;

    @NotNull
    @PasswordValidation
    private String password;

    @NotNull
    @PasswordValidation
    private String passwordConfirmation;

    @JsonIgnore
    public boolean isPasswordsMatched() {
        return Objects.nonNull(password) && password.equals(passwordConfirmation);
    }

}
