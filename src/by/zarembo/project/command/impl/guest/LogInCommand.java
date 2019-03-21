package by.zarembo.project.command.impl.guest;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
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

/**
 * The type Log in command.
 */
public class LogInCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String email = request.getParameter(CommandConstant.EMAIL);
        String password = request.getParameter(CommandConstant.PASSWORD);
        if (UserValidator.validateLogInData(email, password)) {
            try {
                String passwordHash = DigestUtils.sha256Hex(password);
                Optional<User> userOptional = userService.takeUserByEmailAndPassword(email, passwordHash);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (userService.isAccountActivated(user.getUserId())) {
                        request.getSession().setAttribute(CommandConstant.USER, user);
                        request.getSession().setAttribute(CommandConstant.ROLE, user.getRole().toString());
                        router.setPagePath(PagePath.PATH_PAGE_MAIN);
                        router.setRedirectRoute();
                    } else {
                        request.getSession().setAttribute(CommandConstant.MESSAGE, "Your account isn't activated!");
                        router.setRedirectRoute();
                        router.setPagePath(PagePath.PATH_PAGE_LOGIN);
                    }
                } else {
                    request.getSession().setAttribute(CommandConstant.MESSAGE, "Invalid email or password");
                    router.setRedirectRoute();
                    router.setPagePath(PagePath.PATH_PAGE_LOGIN);
                }
            } catch (ServiceException e) {
                throw new CommandException("Log in error", e);
            }
        } else {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Correct data,please");
            router.setRedirectRoute();
            router.setPagePath(PagePath.PATH_PAGE_LOGIN);
        }
        return router;
    }
}
