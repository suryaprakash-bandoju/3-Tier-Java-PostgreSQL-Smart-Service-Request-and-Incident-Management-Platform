package com.example.servicerequest.controller;
import com.example.servicerequest.service.DashboardService;
import com.example.servicerequest.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final DashboardService dashboardService;
    private final RequestService requestService;

    public ManagerController(DashboardService dashboardService, RequestService requestService) {
        this.dashboardService = dashboardService;
        this.requestService = requestService;
    }

    @GetMapping("/dashboard")
    public String managerDashboard(Model model) {
        model.addAttribute("stats", dashboardService.getStats());
        model.addAttribute("requests", requestService.getAllRequests());
        return "manager/dashboard";
    }

    @PostMapping("/requests/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        requestService.updateRequestStatus(id, status);
        return "redirect:/manager/dashboard";
    }
}