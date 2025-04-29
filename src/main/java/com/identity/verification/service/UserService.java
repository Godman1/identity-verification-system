package com.identity.verification.service;

import com.identity.verification.dto.request.RegistrationRequest;
import com.identity.verification.entity.User;
import com.identity.verification.entity.VerificationStatus;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    String registerUser(RegistrationRequest request);
    String updateUser(Long id,RegistrationRequest request);
    String deleteUser(Long id);
    String updateVerificationStatus(Long id, VerificationStatus status);
    List<User> searchUsers(String keyword);
    Resource exportUserCsv();

}
