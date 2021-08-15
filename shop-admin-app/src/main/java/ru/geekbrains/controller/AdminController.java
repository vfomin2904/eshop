package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.User;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.RolesService;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller("/")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private ProductService productsService;

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
            System.out.println("!!"+result.hasErrors());
            return "user_form";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/user/remove/{id}")
    public String removeUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("/product")
    public String getProductPage(Model model){
        List<Product> products = productsService.findAll();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/product/change/{id}")
    public String getCategoryProductPage(Model model, @PathVariable("id") Long id){
        model.addAttribute("product", productsService.findById(id));
        return "product_form";
    }

    @GetMapping("/product/remove/{id}")
    public String removeProduct(@PathVariable("id") Long id){
        productsService.deleteById(id);
        return "redirect:/product";
    }

    @PostMapping("/product")
    public String updateProduct(@Valid @ModelAttribute("product") Product product, BindingResult result){

        if (result.hasErrors()) {
            return "product_form";
        }

        productsService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/category")
    public String getCategoryPage(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "category";}

    @GetMapping("/category/change/{id}")
    public String getCategoryChangePage(Model model, @PathVariable("id") Long id){
        model.addAttribute("category", categoryService.findById(id));
        return "category_form";
    }

    @GetMapping("/category/remove/{id}")
    public String removeCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return "redirect:/category";
    }

    @PostMapping("/category")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryDto category, BindingResult result){

        if (result.hasErrors()) {
            return "category_form";
        }

        categoryService.save(category);
        return "redirect:/category";
    }
}
