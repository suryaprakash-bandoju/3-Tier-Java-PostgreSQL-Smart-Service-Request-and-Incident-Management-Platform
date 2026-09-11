package com.example.servicerequest.service;
import com.example.servicerequest.model.*;
import com.example.servicerequest.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepo;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;

    public RequestService(RequestRepository requestRepo, CommentRepository commentRepo, UserRepository userRepo) {
        this.requestRepo = requestRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
    }

    public List<ServiceRequest> getAllRequests() { return requestRepo.findAll(); }
    public ServiceRequest getRequestById(Long id) { return requestRepo.findById(id).orElseThrow(); }
    
    public ServiceRequest createRequest(ServiceRequest request, User creator) {
        request.setCreatedBy(creator);
        return requestRepo.save(request);
    }

    public void addComment(Long requestId, String text, User user) {
        ServiceRequest request = getRequestById(requestId);
        Comment comment = new Comment();
        comment.setText(text);
        comment.setServiceRequest(request);
        comment.setUser(user);
        commentRepo.save(comment);
    }

    public void updateRequestStatus(Long requestId, String status) {
        ServiceRequest request = getRequestById(requestId);
        request.setStatus(com.example.servicerequest.enums.RequestStatus.valueOf(status));
        requestRepo.save(request);
    }

    public void assignRequest(Long requestId, Long agentId) {
        ServiceRequest request = getRequestById(requestId);
        User agent = userRepo.findById(agentId).orElseThrow();
        request.setAssignedTo(agent);
        request.setStatus(com.example.servicerequest.enums.RequestStatus.ASSIGNED);
        requestRepo.save(request);
    }
}