package com.cashwu.javatest;

import com.cashwu.javatest.controllers.LoginController;
import com.cashwu.javatest.services.LoginProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author cash.wu
 * @since 2024/05/10
 */
@ExtendWith(MockitoExtension.class)
public class LoginControllerUnitTests {

    @Mock
    private Model model;

    @Mock
    private LoginProcessor loginProcessor;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void loginPostLoginSuccessTests() {

        given(loginProcessor.login()).willReturn(true);

        String result = loginController.loginPost("cash", "pw-1234", model);

        assertEquals("login.html", result);

        verify(model).addAttribute("message", "You are now logged in.");
    }

    @Test
    public void loginPostLoginFailTests() {

        given(loginProcessor.login()).willReturn(false);

        String result = loginController.loginPost("cash", "pw-1234", model);

        assertEquals("login.html", result);

        verify(model).addAttribute("message", "Login failed!");
    }

}
