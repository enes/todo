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

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoListControllerTest {

    @InjectMocks
    private TodoListController controller;

    @Mock
    private CookieService cookieService;

    @Mock
    private UserService userService;

    @Mock
    private TodoService todoService;

    @Mock
    private HttpServletRequest request;

    @Test
    public void shouldNotGetTodosWhenTokenIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn(null);
        controller.list(request);
        verifyZeroInteractions(userService);
        verifyZeroInteractions(todoService);
    }

    @Test
    public void shouldNotGetTodosWhenTokenIsNotNullButUserIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("QWEQWE");
        when(userService.findByToken("QWEQWE")).thenReturn(null);
        controller.list(request);
        verify(userService).findByToken("QWEQWE");
        verifyZeroInteractions(todoService);
    }

    @Test
    public void shouldGetTodosWhenBuyerIsNotNull() {
        Users users = new Users();
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("QWEQWE");
        when(userService.findByToken("QWEQWE")).thenReturn(users);
        controller.list(request);
        verify(userService).findByToken("QWEQWE");
        verify(todoService).findByUser(users);
    }


    @Test
    public void shouldNotDeleteWhenCookieIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn(null);
        controller.delete(1L, request);
        verifyZeroInteractions(todoService);
    }

    @Test
    public void shouldNotDeleteWhenUserIsNull() {
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("ABC");
        when(userService.findByToken("ABC")).thenReturn(null);
        controller.delete(1L, request);
        verifyZeroInteractions(todoService);
    }

    @Test
    public void shouldNotDeleteWhenTodoIsNull() {
        Users users = new Users();
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("ABC");
        when(userService.findByToken("ABC")).thenReturn(users);
        when(todoService.findByIdAndUser(1L, users)).thenReturn(null);
        controller.delete(1L, request);
        verify(todoService, times(0)).delete(anyObject());
    }

    @Test
    public void shouldDeleteWhenTodoIsExist() {
        Users users = new Users();
        Todos todo = new Todos();
        when(cookieService.getCookieValue(request, Constants.COOKIE_NAME)).thenReturn("ABC");
        when(userService.findByToken("ABC")).thenReturn(users);
        when(todoService.findByIdAndUser(1L, users)).thenReturn(todo);
        controller.delete(1L, request);
        verify(todoService).delete(todo);
    }

}
