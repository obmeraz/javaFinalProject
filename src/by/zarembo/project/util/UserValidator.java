package by.zarembo.project.util;

import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;

import java.util.List;

/**
 * The type User validator.
 */
public class UserValidator {
    private static final String REGEX_NAME = "^[A-ZЁА-Я]([a-z]{1,20}|[а-яё]{1,20})$";
    private static final String REGEX_NICKNAME = "^[A-Za-zА-ЯЁа-яё\\d]{1,20}$";
    private static final String REGEX_PASSWORD = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static UserService userService = new UserService();

    /**
     * Sign up validation boolean.
     *
     * @param firstName      the first name
     * @param lastName       the last name
     * @param repeatPassword the repeat password
     * @param nickname       the nickname
     * @param email          the email
     * @param password       the password
     * @param messages       the messages
     * @return the boolean
     * @throws ServiceException the service exception
     */
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

        if (!validateName(firstName)) {
            messages.add("Invalid First Name");
            isValid = false;
        }
        if (!lastName.matches(REGEX_NAME)) {
            messages.add("Invalid Last Name");
            isValid = false;
        }
        if (!validateEmail(email)) {
            messages.add("Invalid Email");
            isValid = false;
        }
        if (!nickname.matches(REGEX_NICKNAME)) {
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

    /**
     * Validate email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(REGEX_EMAIL);
    }

    /**
     * Validate name boolean.
     *
     * @param firstName the first name
     * @return the boolean
     */
    static boolean validateName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        return firstName.matches(REGEX_NAME);
    }

    /**
     * Validate password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.matches(REGEX_PASSWORD);
    }


    /**
     * Validate log in data boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     */
    public static boolean validateLogInData(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return (validateEmail(email) && validatePassword(password));
    }
}
