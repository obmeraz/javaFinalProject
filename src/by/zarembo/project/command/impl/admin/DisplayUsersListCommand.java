package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DisplayUsersListCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        List<User> users;
        Router router = new Router();
        try {
            users = userService.takeAllUsers();
            router.setPagePath(PagePath.PATH_PAGE_ADMIN);
            request.setAttribute(AttributeConstant.USERS, users);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
