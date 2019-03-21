package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;
import by.zarembo.project.util.UserValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Change email command.
 */
public class ChangeEmailCommand implements Command {
    private static final String NEW_EMAIL = "new_email";
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        String newEmail = request.getParameter(NEW_EMAIL);
        User user = (User) request.getSession().getAttribute(CommandConstant.USER);
        try {
            if (!userService.checkIsEmailAlreadyExists(newEmail)) {
                if (UserValidator.validateEmail(newEmail)) {
                    userService.changeEmail(newEmail, user);
                    router.setPagePath(PagePath.PATH_PAGE_USER);
                    router.setRedirectRoute();
                } else {
                    request.getSession().setAttribute(CommandConstant.MESSAGE, "Invalid email");
                    router.setPagePath(path);
                    router.setRedirectRoute();
                }
            } else {
                request.getSession().setAttribute(CommandConstant.MESSAGE, "This email is already exists");
                router.setPagePath(path);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
