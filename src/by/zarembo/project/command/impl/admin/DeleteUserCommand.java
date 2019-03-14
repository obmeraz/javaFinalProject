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

public class DeleteUserCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(AttributeConstant.CURRENT_PAGE));
        String id = request.getParameter(AttributeConstant.USER_ID);
        if (id == null || id.isEmpty()) {
            request.getSession().setAttribute(AttributeConstant.MESSAGE, "Doesn't exist");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        try {
            long userId = Long.parseLong(id);
            userService.deleteUserById(userId);
            router.setPagePath(path);
            router.setRedirectRoute();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
