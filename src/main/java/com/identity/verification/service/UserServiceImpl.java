package com.identity.verification.service;

import com.identity.verification.dto.request.RegistrationRequest;
import com.identity.verification.entity.User;
import com.identity.verification.entity.VerificationStatus;
import com.identity.verification.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NinVerificationService verificationService;

    @Autowired
    private ActivityLogService activityLogService;





    @Override
    public String registerUser(RegistrationRequest request) {
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new RuntimeException("phone number already registered");
        }

        if(userRepository.existsByNin(request.getNin())){
            throw new RuntimeException("NIN already registered");
        }

        if(!verificationService.verifyNin(request.getNin())){
            throw new RuntimeException("invalid NIN format");
        }

        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setNin(request.getNin());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setStatus(VerificationStatus.PENDING);


        userRepository.save(user);
        return "User Registered and Pending Verification";

    }

    @Override
    public String updateUser(Long id, RegistrationRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getNin() != null && !request.getNin().isBlank()) {
            user.setNin(request.getNin());
        }

        userRepository.save(user);
        return "User details updated successfully.";
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
        return "User deleted Successfully";
    }

    @Override
    public String updateVerificationStatus(Long id, VerificationStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);
        userRepository.save(user);

        activityLogService.log("Admin", "Changed status of user ID " + id + " to " + status);

        return "User verification status updated to: " + status;
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }

    @Override
    public Resource exportUserCsv() {
        List<User> approvedUsers = userRepository.findByStatus(VerificationStatus.APPROVED);

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("ID,First Name,Last Name,Phone Number,NIN,Status,Registered At\n");

        for (User user : approvedUsers) {
            csvBuilder.append(user.getId()).append(",")
                    .append(user.getFirstName()).append(",")
                    .append(user.getLastName()).append(",")
                    .append(user.getPhoneNumber()).append(",")
                    .append(user.getNin()).append(",")
                    .append(user.getStatus()).append(",")
                    .append(user.getCreatedAt()).append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes();
        return new ByteArrayResource(csvBytes);
    }
}
