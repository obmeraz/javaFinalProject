package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;
import by.zarembo.project.util.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class ChangePasswordCommand implements Command {
    private static final String USER_PREVIOUS_PASSWORD = "previous_password";
    private static final String USER_NEW_PASSWORD = "new_password";
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String oldPassword = request.getParameter(USER_PREVIOUS_PASSWORD);
        String newPassword = request.getParameter(USER_NEW_PASSWORD);
        String path = String.valueOf(request.getSession().getAttribute(AttributeConstant.CURRENT_PAGE));
        User user = (User) request.getSession().getAttribute(AttributeConstant.USER);
        try {
            if (UserValidator.validatePassword(oldPassword) && UserValidator.validatePassword(newPassword)) {
                if (userService.changePassword(oldPassword, newPassword, user)) {
                    router.setPagePath(PagePath.PATH_PAGE_USER);
                    router.setRedirectRoute();
                } else {
                    request.getSession().setAttribute(AttributeConstant.MESSAGE, "Wrong previous password");
                    router.setPagePath(path);
                    router.setRedirectRoute();
                }
            } else {
                request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid password");
                router.setPagePath(path);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
