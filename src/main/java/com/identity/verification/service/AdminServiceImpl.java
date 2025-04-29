package com.identity.verification.service;

import com.identity.verification.dto.request.LoginRequest;
import com.identity.verification.dto.response.LoginResponse;
import com.identity.verification.entity.Admin;
import com.identity.verification.entity.User;
import com.identity.verification.entity.VerificationStatus;
import com.identity.verification.repository.AdminRepository;
import com.identity.verification.repository.UserRepository;
import com.identity.verification.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public String register(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return "Admin registered successfully";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtService.generateToken(admin.getEmail());
        return new LoginResponse(token); // Return token in response
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }




}
