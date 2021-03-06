package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Log out command.
 */
public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        request.getSession().invalidate();
        router.setPagePath(PagePath.PATH_PAGE_MAIN);
        router.setRedirectRoute();
        return router;
    }
}
