package com.identity.verification.controller;

import com.identity.verification.dto.request.LoginRequest;
import com.identity.verification.dto.request.RegistrationRequest;
import com.identity.verification.dto.response.LoginResponse;
import com.identity.verification.entity.ActivityLog;
import com.identity.verification.entity.Admin;
import com.identity.verification.entity.User;
import com.identity.verification.entity.VerificationStatus;
import com.identity.verification.repository.ActivityLogRepository;
import com.identity.verification.service.AdminService;
import com.identity.verification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private final AdminService adminService;
    private final UserService userService;
    private final ActivityLogRepository activityLogRepository;

    public AdminController(AdminService adminService, UserService userService, ActivityLogRepository activityLogRepository) {
        this.adminService = adminService;
        this.userService = userService;
        this.activityLogRepository = activityLogRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Admin admin){
        return ResponseEntity.ok((adminService.register(admin)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(adminService.login(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approveUser(@PathVariable Long id) {
        String message = userService.updateVerificationStatus(id, VerificationStatus.APPROVED);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/users/{id}/reject")
    public ResponseEntity<String> rejectUser(@PathVariable Long id) {
        String message = userService.updateVerificationStatus(id, VerificationStatus.REJECTED);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/users/{id}/update")
    public ResponseEntity<String> updateUser(@PathVariable Long id, RegistrationRequest request){
        String message = userService.updateUser(id,request);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/users/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        List<User> users = userService.searchUsers(keyword);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/export")
    public ResponseEntity<Resource> exportApprovedUsers() {
        Resource file = userService.exportUserCsv();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=approved_users.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @GetMapping("/users/logs")
    public ResponseEntity<List<ActivityLog>> getLogs() {
        return ResponseEntity.ok(activityLogRepository.findAll());
    }






}
