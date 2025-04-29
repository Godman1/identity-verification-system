package com.identity.verification.controller;


import com.identity.verification.dto.request.RegistrationRequest;
import com.identity.verification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/verify")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request){
        String response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }
}
