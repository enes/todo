package com.todo.controller;


import com.todo.domain.Users;
import com.todo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpControllerTest {

    @InjectMocks
    private SignUpController controller;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    @Mock
    private PrintWriter printWriter;
    @Test
    public void shouldNotSaveUserWhenHasErrors() throws IOException {
        Users user = new Users();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(response.getWriter()).thenReturn(printWriter);
        controller.save(user, bindingResult, response);
        verifyZeroInteractions(userService);
    }

    @Test
    public void shouldSaveUserWhenHasNoErrors() throws IOException {
        Users user = new Users();
        user.setToken("ASDASD");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(response.getWriter()).thenReturn(printWriter);
        controller.save(user, bindingResult, response);
        verify(userService).save(user);
    }

    @Test
    public void shouldMapContainsErrorWhenRequestContainsResult() throws IOException {
        ModelMap map = new ModelMap();
        when(request.getParameter("result")).thenReturn("false");
        when(response.getWriter()).thenReturn(printWriter);
        String result = controller.signUp(map, request);
        assertThat(map.get("error"), equalTo(true));
        assertThat(result, equalTo("signup"));

    }
}
