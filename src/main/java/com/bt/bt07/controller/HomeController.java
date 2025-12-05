package com.bt.bt07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        // Chuyển hướng về trang AJAX Video (Trang chính của bài tập này)
        return "redirect:/admin/videos-ajax";
    }
}