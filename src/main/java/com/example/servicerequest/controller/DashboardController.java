package com.example.servicerequest.controller;
import com.example.servicerequest.model.User;
import com.example.servicerequest.service.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;
    public DashboardController(DashboardService dashboardService) { this.dashboardService = dashboardService; }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("stats", dashboardService.getStats());
        return "dashboard";
    }
}