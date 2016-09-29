package com.todo.controller;

import com.todo.component.Constants;
import com.todo.domain.Todos;
import com.todo.domain.Users;
import com.todo.service.CookieService;
import com.todo.service.TodoService;
import com.todo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CookieService cookieService;

    @Test
    public void shouldNotCreateTodoWhenCookieIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("");
        todoController.createTodo(new Todos(), request);
        verifyZeroInteractions(todoService);
    }

    @Test
    public void shouldNotCreateTodoWhenBuyerIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("123");
        todoController.createTodo(new Todos(), request);
        verifyZeroInteractions(todoService);
    }

    @Test
    public void shouldCreateTodoAndLoadTodos() {
        Todos todos = new Todos();
        Users user = new Users();
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("123");
        when(userService.findByToken("123")).thenReturn(user);
        todoController.createTodo(todos, request);
        verify(todoService).save(todos);
        verify(todoService).findByUser(user);
    }

    @Test
    public void shouldReturnLoginPageWhenCookieIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("");
        String pageName = todoController.todo(new ModelMap(), request, response);
        assertThat(pageName, equalTo("login"));
    }

    @Test
    public void shouldReturnLoginPageWhenUserIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("123");
        when(userService.findByToken("123")).thenReturn(null);
        String pageName = todoController.todo(new ModelMap(), request, response);
        assertThat(pageName, equalTo("login"));
    }

    @Test
    public void shouldReturnCreatePageWhenUserIsExist() {
        Users user = new Users();
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("123");
        when(userService.findByToken("123")).thenReturn(user);
        String pageName = todoController.todo(new ModelMap(), request, response);
        assertThat(pageName, equalTo("createtodo"));
    }

}