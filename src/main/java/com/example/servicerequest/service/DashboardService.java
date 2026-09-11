package com.example.servicerequest.service;
import com.example.servicerequest.enums.RequestStatus;
import com.example.servicerequest.repository.RequestRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {
    private final RequestRepository requestRepo;
    public DashboardService(RequestRepository requestRepo) { this.requestRepo = requestRepo; }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("open", requestRepo.countByStatus(RequestStatus.OPEN));
        stats.put("inProgress", requestRepo.countByStatus(RequestStatus.IN_PROGRESS));
        stats.put("resolved", requestRepo.countByStatus(RequestStatus.RESOLVED));
        stats.put("closed", requestRepo.countByStatus(RequestStatus.CLOSED));
        return stats;
    }
}