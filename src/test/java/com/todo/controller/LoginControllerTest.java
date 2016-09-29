package com.todo.controller;

import com.todo.component.Constants;
import com.todo.domain.Users;
import com.todo.model.UserModel;
import com.todo.service.CookieService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LoginController controller;

    @Mock
    private CookieService cookieService;

    @Mock
    private UserService userService;

    @Test
    public void shouldDeleteCookieAndReturnLogin() {
        String login = controller.logout(response);
        verify(cookieService).deleteCookie(Constants.COOKIE_NAME, response);
        assertThat(login, equalTo("login"));
    }

    @Test
    public void shouldRedirectLoginPageWhenUserMailIsNotValid() {
        UserModel userModel = new UserModel();
        userModel.setEmail("eneskantepe@gmailcom");
        String login = controller.login(userModel, new ModelMap(), response);
        assertThat(login, equalTo("login"));
    }

    @Test
    public void shouldRedirectLoginPageWhenUserMailValidAndPasswordIsNull() {
        UserModel userModel = new UserModel();
        userModel.setEmail("eneskantepe@gmailcom");
        String login = controller.login(userModel, new ModelMap(), response);
        assertThat(login, equalTo("login"));
    }

    @Test
    public void shouldReturnLoginPageWhenUserIsNull() {
        UserModel userModel = new UserModel();
        userModel.setEmail("eneskantepe@gmail.com");
        userModel.setPassword("123");
        when(userService.findByEmail(userModel.getEmail())).thenReturn(null);
        String login = controller.login(userModel, new ModelMap(), response);
        assertThat(login, equalTo("login"));

    }

    @Test
    public void shouldReturnLoginPageWhenUsersPasswordIsNotCorrect() {
        Users user = new Users();
        user.setEmail("eneskantepe@gmail.com");
        user.setPassword("46f94c8de14fb36680850768ff1b7f2");
        user.setToken("123");
        UserModel userModel = new UserModel();
        userModel.setEmail("eneskantepe@gmail.com");
        userModel.setPassword("123qwe");
        when(userService.findByEmail(userModel.getEmail())).thenReturn(user);
        String login = controller.login(userModel, new ModelMap(), response);
        assertThat(login, equalTo("login"));
    }

    @Test
    public void shouldReturnTodoPageAndPersistCookie() {
        Users user = new Users();
        user.setEmail("eneskantepe@gmail.com");
        user.setPassword("46f94c8de14fb36680850768ff1b7f2a");
        user.setToken("123");
        UserModel userModel = new UserModel();
        userModel.setEmail("eneskantepe@gmail.com");
        userModel.setPassword("123qwe");
        when(userService.findByEmail(userModel.getEmail())).thenReturn(user);
        String login = controller.login(userModel, new ModelMap(), response);
        verify(cookieService).persistCookie(response, Constants.COOKIE_NAME, "123");
        assertThat(login, equalTo("createtodo"));
    }
}