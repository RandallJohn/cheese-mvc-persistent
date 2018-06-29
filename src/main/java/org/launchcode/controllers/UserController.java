package org.launchcode.controllers;


import org.launchcode.models.User;
import org.launchcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "add")
    public String add(Model model) {
        model.addAttribute("title", "User Signup");
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute User user, Errors errors, String verify){
        List<User> sameName = userDao.findByusername(user.getUsername());
        if (verify.equals(user.getPassword()) && errors.hasErrors()==false && sameName.isEmpty()) {
            model.addAttribute("user", user);
            userDao.save(user);
            return "user/index";
        }
        else{
            if (!sameName.isEmpty()) {
                model.addAttribute("title", "User Signup");
                model.addAttribute("message", "Passwords do not match");
                model.addAttribute("duplicate", "That username is taken!");
                return "user/add";
            }

            model.addAttribute("title", "User Signup");
            model.addAttribute("message", "Passwords do not match");
            return "user/add";
        }
    }

}