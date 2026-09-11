package com.example.servicerequest;

import com.example.servicerequest.model.ServiceRequest;
import com.example.servicerequest.model.User;
import com.example.servicerequest.enums.Priority;
import com.example.servicerequest.enums.RequestStatus;
import com.example.servicerequest.repository.RequestRepository;
import com.example.servicerequest.repository.CommentRepository;
import com.example.servicerequest.repository.UserRepository;
import com.example.servicerequest.service.RequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepo;
    @Mock
    private CommentRepository commentRepo;
    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private RequestService requestService;

    @Test
    public void testCreateRequest() {
        User mockUser = new User();
        mockUser.setUsername("testuser");

        ServiceRequest request = new ServiceRequest();
        request.setTitle("Test Request");
        request.setPriority(Priority.HIGH);

        when(requestRepo.save(any(ServiceRequest.class))).thenAnswer(invocation -> {
            ServiceRequest saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        ServiceRequest result = requestService.createRequest(request, mockUser);

        assertEquals(1L, result.getId());
        assertEquals("Test Request", result.getTitle());
        assertEquals(RequestStatus.OPEN, result.getStatus());
        assertEquals(mockUser, result.getCreatedBy());
    }
}