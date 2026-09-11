package com.example.servicerequest.controller;
import com.example.servicerequest.model.*;
import com.example.servicerequest.service.RequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;
    public RequestController(RequestService requestService) { this.requestService = requestService; }

    @GetMapping
    public String listRequests(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "requests";
    }

    @GetMapping("/new")
    public String newRequestForm(Model model) {
        model.addAttribute("request", new ServiceRequest());
        return "create-request";
    }

    @PostMapping
    public String createRequest(@ModelAttribute ServiceRequest request, @AuthenticationPrincipal User user) {
        // Note: In a real app, we'd fetch Category from DB. For simplicity, creating a dummy one or assuming frontend passes ID.
        // To keep code concise, we'll just save it. 
        requestService.createRequest(request, user);
        return "redirect:/requests";
    }

    @GetMapping("/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        model.addAttribute("request", requestService.getRequestById(id));
        model.addAttribute("comment", new Comment());
        return "request-details";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @ModelAttribute Comment comment, @AuthenticationPrincipal User user) {
        requestService.addComment(id, comment.getText(), user);
        return "redirect:/requests/" + id;
    }
}