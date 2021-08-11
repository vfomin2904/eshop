package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.persist.User;
import ru.geekbrains.service.RolesService;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

@Controller("/")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public String getMainPage(){return "index";}

    @GetMapping("/user")
    public String getUserPage(Model model){
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    @GetMapping("/user/change/{id}")
    public String getUserChangePage(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", rolesService.findAll());
        return "user_form";
    }

    @PostMapping("/user")
    public String updateUser(@Valid @ModelAttribute("user")  UserDto user, BindingResult result){

        if(!user.getPassword().equals(user.getRepeatPassword())){
            result.rejectValue("password", "", "Пароли не совпадают");
        }

        if (result.hasErrors()) {
            return "user_form";
        }

        userService.save(user);
        return "redirect:/user";
    }


    @GetMapping("/product")
    public String getProductPage(){return "product";}

    @GetMapping("/category")
    public String getCategoryPage(){return "category";}
}
