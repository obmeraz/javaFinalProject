package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.util.LifeHackValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Edit lifehack command.
 */
public class EditLifeHackCommand implements Command {
    private static final String ID = "id";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String id = request.getParameter(CommandConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Invalid id");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
        }
        long lifeHackId = Long.parseLong(id);
        request.getSession().setAttribute(ID, lifeHackId);
        router.setPagePath(PagePath.PATH_PAGE_EDIT_LIFEHACK);
        return router;
    }
}
