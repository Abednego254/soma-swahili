package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.LoginRequestDto;
import com.abednego.somaSwahili.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);

}
