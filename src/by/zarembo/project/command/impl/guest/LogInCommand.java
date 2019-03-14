package by.zarembo.project.command.impl.guest;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;
import by.zarembo.project.util.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LogInCommand implements Command {
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String ATTRIBUTE_ROLE = "role";

    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String email = request.getParameter(PARAM_EMAIL);
        String password = DigestUtils.sha256Hex(request.getParameter(PARAM_PASSWORD));
        if (UserValidator.validateLogInData(email, password)) {
            try {
                Optional<User> userOptional = userService.takeUserByEmailAndPassword(email, password);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (userService.isAccountActivated(user.getUserId())) {
                        request.getSession().setAttribute(AttributeConstant.USER, user);
                        request.getSession().setAttribute(ATTRIBUTE_ROLE, user.getRole().toString());
                        router.setPagePath(PagePath.PATH_PAGE_MAIN);
                        router.setRedirectRoute();
                    } else {
                        request.getSession().setAttribute(AttributeConstant.MESSAGE, "Your account isn't activated!");
                        router.setRedirectRoute();
                        router.setPagePath(PagePath.PATH_PAGE_LOGIN);
                    }
                } else {
                    request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid email or password");
                    router.setRedirectRoute();
                    router.setPagePath(PagePath.PATH_PAGE_LOGIN);
                }
            } catch (ServiceException e) {
                throw new CommandException("Log in error", e);
            }
        } else {
            router.setPagePath(PagePath.PATH_PAGE_LOGIN);
        }
        return router;
    }
}
