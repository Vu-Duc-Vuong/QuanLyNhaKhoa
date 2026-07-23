package com.qlnhakhoa.auth.service;

import com.qlnhakhoa.auth.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

}