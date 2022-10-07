package com.pnc.registration.service;

import com.pnc.registration.model.domain.RegistrationResponseData;
import com.pnc.registration.model.domain.User;

public interface RegistrationService {
    RegistrationResponseData register(User user);
}
