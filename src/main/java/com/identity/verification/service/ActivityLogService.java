package com.identity.verification.service;

import org.springframework.stereotype.Service;

@Service
public interface ActivityLogService {
    void log(String performedBy, String action);
}
