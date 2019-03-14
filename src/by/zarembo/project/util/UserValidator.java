package by.zarembo.project.util;

import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;

import java.util.List;

public class UserValidator {
    private static final String REGEX_NAME = "^[A-ZЁА-Я]([a-z]{1,20}|[а-яё]{1,20})$";
    private static final String REGEX_NICKNAME = "^[A-Za-zА-ЯЁа-яё\\d]{1,20}$";
    private static final String REGEX_PASSWORD = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static UserService userService = new UserService();

    public static boolean signUpValidation(String firstName, String lastName, String repeatPassword, String nickname,
                                           String email, String password,
                                           List<String> messages) throws ServiceException {
        if (firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || password == null || password.isEmpty()
                || repeatPassword == null || repeatPassword.isEmpty()
                || nickname == null || nickname.isEmpty()
                || email == null || email.isEmpty()) {
            messages.add("Fill in the blank fields");
            return false;
        }

        boolean isValid = true;

        if (!validatePassword(repeatPassword)) {
            messages.add("Passwords don't match");
            isValid = false;
        }

        if (!(firstName.matches(REGEX_NAME))) {
            messages.add("Invalid First Name");
            isValid = false;
        }
        if (!(lastName.matches(REGEX_NAME))) {
            messages.add("Invalid Last Name");
            isValid = false;
        }
        if (!validateEmail(email)) {
            messages.add("Invalid Email");
            isValid = false;
        }
        if (!(nickname.matches(REGEX_NICKNAME))) {
            messages.add("Invalid Nickname");
            isValid = false;
        }

        if (userService.checkIsUserNameAlreadyExists(nickname)) {
            messages.add("Username is already exist");
            isValid = false;
        }

        if (userService.checkIsEmailAlreadyExists(email)) {
            messages.add("Email is already exist");
            isValid = false;
        }

        if (!validatePassword(password)) {
            messages.add("Invalid Password");
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(REGEX_EMAIL);
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.matches(REGEX_PASSWORD);
    }

    public static boolean validateLogInData(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return (email.matches(REGEX_EMAIL) /*&& validate(password, REGEX_PASSWORD)*/);
    }
}
