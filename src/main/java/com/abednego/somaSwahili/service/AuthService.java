package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.auth.LoginRequestDto;
import com.abednego.somaSwahili.dto.auth.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);

}
