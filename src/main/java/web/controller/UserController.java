package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;
import web.validator.UserValidator;

@Controller
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private static final String REDIRECT = "redirect:/";

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping(value = "/")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/users";
    }

    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user/new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "age", required = false) Integer age,
                           Model model) {
        User user = new User(firstName, lastName, email, age);
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", result.getAllErrors());
            return "user/new_user";
        }
        userService.addUser(user);
        return REDIRECT;
    }

    @GetMapping(value = "/edit")
    public String editUserForm(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/update_user";
    }

    @PostMapping(value = "/update")
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam(value = "age", required = false) Integer age,
                             Model model) {
        User user = userService.getUserById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAge(age);
        BindingResult result = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getFieldErrors());
            model.addAttribute("user", user);
            return "user/update_user";
        }
        userService.updateUser(user);
        return REDIRECT;
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return REDIRECT;
    }

//    private boolean isValidUserProps(User user, Model model) {}
}
