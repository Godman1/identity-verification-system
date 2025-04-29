package com.identity.verification.service;

import com.identity.verification.entity.ActivityLog;
import com.identity.verification.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityLogServiceImpl implements ActivityLogService{

    private ActivityLogRepository activityLogRepository;

    @Override
    public void log(String performedBy, String action) {
        ActivityLog log = new ActivityLog();
        log.setPerformedBy(performedBy);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        activityLogRepository.save(log);

    }
}
