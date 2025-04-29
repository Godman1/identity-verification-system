package com.identity.verification.service;

import com.identity.verification.dto.request.LoginRequest;
import com.identity.verification.dto.response.LoginResponse;
import com.identity.verification.entity.Admin;
import com.identity.verification.entity.User;
import com.identity.verification.entity.VerificationStatus;

import java.util.List;

public interface AdminService {

    String register (Admin admin);
    LoginResponse login (LoginRequest request);
    List<User> getAllUsers();



}
