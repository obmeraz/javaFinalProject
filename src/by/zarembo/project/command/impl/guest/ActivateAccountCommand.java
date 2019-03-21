package by.zarembo.project.command.impl.guest;

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
 * The type Activate account command.
 */
public class ActivateAccountCommand implements Command {
    private static final String USER_HASH_DATA = "hash";
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String passwordHash = request.getParameter(USER_HASH_DATA);
        try {
            if (passwordHash != null && !passwordHash.isEmpty()) {
                Optional<User> userOptional = userService.takeUserByPassword(passwordHash);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    userService.activateUserAccount(user.getUserId());
                    router.setPagePath(PagePath.PATH_PAGE_ACTIVATION);
                    router.setRedirectRoute();
                } else {
                    request.getSession().setAttribute(CommandConstant.MESSAGE, "Not found information");
                    router.setPagePath(PagePath.PATH_PAGE_ERROR);
                    router.setRedirectRoute();
                }
            }else{
                request.getSession().setAttribute(CommandConstant.MESSAGE, "Error");
                router.setPagePath(PagePath.PATH_PAGE_ERROR);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
