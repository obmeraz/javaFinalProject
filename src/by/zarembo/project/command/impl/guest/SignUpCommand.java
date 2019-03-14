package by.zarembo.project.command.impl.guest;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;
import by.zarembo.project.util.EmailType;
import by.zarembo.project.util.EmailSender;
import by.zarembo.project.util.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpCommand implements Command {
    private static final String PARAM_FIRST_NAME = "firstname";
    private static final String PARAM_LAST_NAME = "lastname";
    private static final String PARAM_NICKNAME = "nickname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_REPEAT_PASSWORD = "repeat_password";
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String nickname = request.getParameter(PARAM_NICKNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        String repeatPassword = request.getParameter(PARAM_REPEAT_PASSWORD);
        Router router = new Router();
        List<String> errorMessages = new ArrayList<>();
        try {
            if (UserValidator.signUpValidation(firstName, lastName, repeatPassword, nickname,
                    email, password, errorMessages)) {
                try {
                    User user = userService.buildUser(firstName, lastName, nickname, email, password);
                    userService.signUpUser(user);
                    EmailSender emailSender = new EmailSender();
                    emailSender.sendEmail(user, EmailType.SIGN_UP);
                    router.setPagePath(PagePath.PATH_PAGE_MAIN);
                    router.setRedirectRoute();
                } catch (ServiceException e) {
                    throw new CommandException("Sign up user error", e);
                }
            } else {
                request.setAttribute("errorMessages", errorMessages);
                router.setPagePath(PagePath.PATH_PAGE_REGISTER);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return router;
    }
}
