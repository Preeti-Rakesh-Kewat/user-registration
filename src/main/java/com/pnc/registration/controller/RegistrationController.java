package com.pnc.registration.controller;

import com.pnc.registration.model.domain.RegistrationResponseDto;
import com.pnc.registration.model.domain.RegistrationRequestDto;

import com.pnc.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(path = "/user-management/v1")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegistrationController {
  //@Autowired
  private final RegistrationService registrationService;



    @PostMapping(path= "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegistrationResponseDto> registerUser(@Valid @RequestBody RegistrationRequestDto user) {

        return new ResponseEntity<>(registrationService.register(user), HttpStatus.CREATED);

    }
}
