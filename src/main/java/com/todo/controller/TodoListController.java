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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TodoListController {

    @Autowired
    private CookieService cookieService;
    @Autowired
    private UserService userService;
    @Autowired
    private TodoService todoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("todolist");
        String tokenFromCookie = cookieService.getCookieValue(request, Constants.COOKIE_NAME);
        if (StringUtils.isNotEmpty(tokenFromCookie)) {
            Users user = userService.findByToken(tokenFromCookie);
            if (user != null) {
                modelAndView.addObject("todos", todoService.findByUser(user));
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("todolist");
        String tokenFromCookie = cookieService.getCookieValue(request, Constants.COOKIE_NAME);
        if (StringUtils.isNotEmpty(tokenFromCookie)) {
            Users user = userService.findByToken(tokenFromCookie);
            if (user != null) {
                Todos todo = todoService.findByIdAndUser(id, user);
                if (todo != null) {
                    todoService.delete(todo);
                }
                modelAndView.addObject("todos", todoService.findByUser(user));
            }
        }
        return modelAndView;
    }
}
