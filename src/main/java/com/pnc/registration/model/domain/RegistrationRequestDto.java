package com.pnc.registration.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequestDto implements Serializable {
    @NotBlank(message = "Username should not be null or empty")
    private String userName;

    @NotBlank(message = "Password should not be null or empty")
    @Size(min = 9, message = "Password need to be greater than 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password need to containing at least 1 number, 1 Capitalized letter, 1 special character in this set “_ # $ % .")
    private String password;

    @NotBlank(message = "IP Address should not be null or empty")
    private String ipAddress;
}
