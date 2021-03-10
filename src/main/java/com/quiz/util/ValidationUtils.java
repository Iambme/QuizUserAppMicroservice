package com.quiz.util;

import com.quiz.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ValidationUtils {
    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}", Pattern.CASE_INSENSITIVE);

    public boolean isValidPassword(String password) throws ValidationException {
        if (password == null) {
            throw new ValidationException("invalid password (null)");
        }
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    public boolean isValidEmail(String email) throws ValidationException {
        if (email == null) {
            throw new ValidationException("invalid email (null)");
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }
}
