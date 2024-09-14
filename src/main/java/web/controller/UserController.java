package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private static final String REDIRECT = "redirect:/";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
                           @RequestParam(value = "age") int age, Model model) {
        User user = new User(firstName, lastName, email, age);
        model.addAttribute("user", user);
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
                             @RequestParam("age") int age) {
        User user = userService.getUserById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAge(age);

        userService.updateUser(user);
        return REDIRECT;
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return REDIRECT;
    }
}
