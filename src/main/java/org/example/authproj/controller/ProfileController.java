package org.example.authproj.controller;
import org.example.authproj.Service.UserService;
import org.example.authproj.model.Photo;
import org.example.authproj.model.User;
import org.example.authproj.repository.PhotoRepository;
import org.example.authproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private final UserService userService;
    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file, Principal principal) throws IOException {
        // Получаем текущего пользователя
        User user = userService.findByUsername(principal.getName());

        // Сохраняем файл на сервер
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/");
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Сохраняем путь к файлу в базе данных
        user.setPhotoPath(fileName);
        userService.registerNewUser(user);

        return "redirect:/profile";
    }

    @GetMapping
    public String profilePage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }
}