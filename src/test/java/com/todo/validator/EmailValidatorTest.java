package com.todo.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @Before
    public void init() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void shouldReturnFalseWhenEmailIsNotValid() {
        boolean validate = emailValidator.validate("eneskantepe@gmailcom");
        assertFalse(validate);
    }

    @Test
    public void shouldReturnTrueWhenEmailIsValid() {
        boolean validate = emailValidator.validate("eneskantepe@gmail.com");
        assertTrue(validate);
    }

    @Test
    public void shouldReturnFalseWhenEmailIsBlank() {
        boolean validate = emailValidator.validate("");
        assertFalse(validate);
    }

}