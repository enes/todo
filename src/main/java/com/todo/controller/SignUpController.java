package com.todo.controller;

import com.todo.domain.Users;
import com.todo.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@Valid Users user, BindingResult result, HttpServletResponse response) throws IOException {
        Map<String, Object> resultJson = new HashMap<>();
        if (result.hasErrors()) {
            resultJson.put("success", false);
            response.getWriter().write(new JSONObject(resultJson).toString());
            return;
        }
        userService.save(user);
        resultJson.put("success", true);
        response.getWriter().write(new JSONObject(resultJson).toString());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(ModelMap map, HttpServletRequest request) {
        String result = request.getParameter("result");
        if (result != null && result.equals("false")) {
            map.put("error", true);
        }
        return "signup";
    }
}
