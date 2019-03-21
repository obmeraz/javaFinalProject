package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Make admin command.
 */
public class MakeAdminCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        String id = request.getParameter(CommandConstant.USER_ID);
        if (id == null || id.isEmpty()) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Invalid user id");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        try {
            long userId = Long.parseLong(id);
            Optional<User> userOptional = userService.takeUserById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userService.giveUserAdminRights(user);
                router.setPagePath(path);
                router.setRedirectRoute();
            } else {
                request.getSession().setAttribute(CommandConstant.MESSAGE, "Can't found user");
                router.setPagePath(PagePath.PATH_PAGE_ERROR);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
