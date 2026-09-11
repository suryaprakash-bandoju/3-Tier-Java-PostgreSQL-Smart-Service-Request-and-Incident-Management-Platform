package com.example.servicerequest.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "request_id")
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public ServiceRequest getServiceRequest() { return serviceRequest; }
    public void setServiceRequest(ServiceRequest serviceRequest) { this.serviceRequest = serviceRequest; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}