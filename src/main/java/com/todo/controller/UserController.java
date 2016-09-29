package com.todo.controller;

import com.todo.service.UserService;
import com.todo.validator.EmailValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/validateMail/{email:.+}", method = RequestMethod.GET)
    public void checkMail(@PathVariable("email") String email, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<>();
        EmailValidator validate = new EmailValidator();
        result.put("success", true);
        boolean isValidMail = validate.validate(email);
        if (!isValidMail) {
            result.put("success", false);
            result.put("message", "Bozuk Email Formati");
        } else if (userService.findByEmail(email) != null) {
            result.put("success", false);
            result.put("message", "Email Adresi Kullanimda");
        }
        JSONObject jsonObject = new JSONObject(result);
        response.getWriter().write(jsonObject.toString());
    }
}
