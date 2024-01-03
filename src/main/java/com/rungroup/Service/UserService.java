package com.rungroup.Service;

import com.rungroup.Domain.Dto.RegistrationDto;
import com.rungroup.Domain.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
