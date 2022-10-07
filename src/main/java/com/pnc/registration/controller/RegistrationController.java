package com.pnc.registration.controller;

import com.pnc.registration.model.domain.RegistrationResponseData;
import com.pnc.registration.model.domain.User;
import com.pnc.registration.service.RegistrationService;
import lombok.AllArgsConstructor;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/user-management/v1")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

private RegistrationService registrationService;

    @PostMapping("/register")
    private ResponseEntity registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.register(user));

    }
}
