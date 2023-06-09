package com.hackaton.controllers;

import com.hackaton.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hackaton.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String info(Model model) {
        String role;
        switch (userService.getCurrentUser().getRole()) {
            case "ROLE_ADMIN" : role = "Администратор"; break;
            case "ROLE_TEACHER" : role = "Учитель"; break;
            default: role = "Студент";
        }

        model.addAttribute("user",userService.getCurrentUser());
        model.addAttribute("role", role);
        return "user/info";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "user/edit";
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute @Valid User user,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(user);
        return "redirect:/user";
    }


}
