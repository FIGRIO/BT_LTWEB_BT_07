package com.bt.bt07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bt.bt07.model.Category;
import com.bt.bt07.model.User;
import com.bt.bt07.model.Video;
import com.bt.bt07.repository.CategoryRepository;
import com.bt.bt07.repository.UserRepository;
import com.bt.bt07.repository.VideoRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final VideoRepository videoRepo;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepo, CategoryRepository categoryRepo, VideoRepository videoRepo,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.videoRepo = videoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Users list
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")
                && !user.getPassword().startsWith("$2b$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepo.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{username}")
    public String editUser(@PathVariable String username, Model model) {
        userRepo.findById(username).ifPresent(u -> model.addAttribute("user", u));
        return "user-form";
    }

    @GetMapping("/users/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        userRepo.deleteById(username);
        return "redirect:/admin/users";
    }

    // Categories list
    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("users", userRepo.findAll());
        return "category-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category, @RequestParam(required = false) String username) {
        if (username != null && !username.isEmpty()) {
            userRepo.findById(username).ifPresent(category::setUser);
        }
        categoryRepo.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        categoryRepo.findById(id).ifPresent(c -> model.addAttribute("category", c));
        model.addAttribute("users", userRepo.findAll());
        return "category-form";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/admin/categories";
    }

    // Videos (Legacy Thymeleaf View - Nếu bạn vẫn muốn giữ)
    @GetMapping("/videos")
    public String videos(Model model, @RequestParam(required = false) String q) {
        List<Video> list;
        if (q != null && !q.isEmpty())
            list = videoRepo.findByTitleContaining(q); // Sửa tên hàm cho khớp với Repository
        else
            list = videoRepo.findAll();

        model.addAttribute("videos", list);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("q", q == null ? "" : q);
        return "videos";
    }

    @GetMapping("/videos-ajax")
    public String videosAjax(Model model) {
        // Lấy danh sách Category để đổ vào Select box trong Modal thêm/sửa
        model.addAttribute("categories", categoryRepo.findAll());
        return "admin/videos_ajax"; // Trả về file videos_ajax.html
    }

}