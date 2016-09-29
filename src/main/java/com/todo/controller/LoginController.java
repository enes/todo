package com.todo.controller;

import com.todo.component.Constants;
import com.todo.domain.Users;
import com.todo.model.UserModel;
import com.todo.service.CookieService;
import com.todo.service.UserService;
import com.todo.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UserModel model, ModelMap map, HttpServletResponse response) {
        EmailValidator emailValidator = new EmailValidator();
        if (userInputsAreValid(model, emailValidator)) {
            Users user = userService.findByEmail(model.getEmail());
            if (user != null && user.getPassword().equals(model.getEncyptPassword())) {
                cookieService.persistCookie(response, Constants.COOKIE_NAME, user.getToken());
                map.addAttribute("redirectUrl", "/todo");
                return "createtodo";
            }
        }
        map.put("error", true);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap map, HttpServletRequest request) {
        String result = request.getParameter("result");
        if (result != null && result.equals("true")) {
            map.put("success", true);
        }
        return "login";
    }

    private boolean userInputsAreValid(UserModel model, EmailValidator emailValidator) {
        return emailIsValid(model, emailValidator) && model.getPassword() != null;
    }

    private boolean emailIsValid(UserModel model, EmailValidator emailValidator) {
        return model.getEmail() != null && emailValidator.validate(model.getEmail());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response) {
        cookieService.deleteCookie(Constants.COOKIE_NAME, response);
        return "login";
    }
}
