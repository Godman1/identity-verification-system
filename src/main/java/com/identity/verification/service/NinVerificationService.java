package com.identity.verification.service;

import org.springframework.stereotype.Service;

@Service
public class NinVerificationService {
    public boolean verifyNin(String nin){
        return nin != null && nin.matches("\\d{11}");
    }


}
