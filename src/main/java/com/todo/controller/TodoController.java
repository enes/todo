package com.todo.controller;

import com.todo.component.Constants;
import com.todo.domain.Todos;
import com.todo.domain.Users;
import com.todo.service.CookieService;
import com.todo.service.TodoService;
import com.todo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TodoController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public String todo(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        String tokenFromCookie = cookieService.getCookieValue(request, Constants.COOKIE_NAME);
        if (StringUtils.isNotBlank(tokenFromCookie)) {
            Users users = userService.findByToken(tokenFromCookie);
            if (users == null) {
                cookieService.deleteCookie(Constants.COOKIE_NAME, response);
                return "login";
            } else {
                map.put("name", users.getUserName());
                return "createtodo";
            }
        }
        return "login";
    }

    @RequestMapping(value = "/createtodo", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createTodo(@ModelAttribute Todos todos, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("todolist");
        String tokenFromCookie = cookieService.getCookieValue(request, Constants.COOKIE_NAME);
        if (StringUtils.isNotBlank(tokenFromCookie)) {
            Users users = userService.findByToken(tokenFromCookie);
            if (users != null) {
                todos.setUser(users);
                todoService.save(todos);
                modelAndView.addObject("todos", todoService.findByUser(users));
            }
        }
        return modelAndView;
    }
}
