package by.zarembo.project.command.impl.common;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;

import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LocaleCommand implements Command {
    private static final String LANGUAGE = "language";
    private static final String SESSION_LOCALE = "session_locale";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(AttributeConstant.CURRENT_PAGE));
        if (path != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(LANGUAGE, request.getParameter(SESSION_LOCALE));
            router.setPagePath(path);
            router.setRedirectRoute();
        } else {
            router.setPagePath(PagePath.PATH_PAGE_MAIN);
        }
        return router;
    }
}
