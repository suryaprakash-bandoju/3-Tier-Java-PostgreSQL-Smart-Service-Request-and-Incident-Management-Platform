package com.example.servicerequest.repository;
import com.example.servicerequest.model.ServiceRequest;
import com.example.servicerequest.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RequestRepository extends JpaRepository<ServiceRequest, Long> {
    long countByStatus(RequestStatus status);
}