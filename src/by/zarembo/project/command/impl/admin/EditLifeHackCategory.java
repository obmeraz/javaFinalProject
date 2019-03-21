package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;
import by.zarembo.project.util.LifeHackValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Edit lifehack category.
 */
public class EditLifeHackCategory implements Command {
    private static final String NEW_LIFEHACK_CATEGORY = "new_category";
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        LifeHack lifeHack;
        String newCategory = request.getParameter(NEW_LIFEHACK_CATEGORY);
        String id = request.getParameter(CommandConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            setRouterConfiguration(PagePath.PATH_PAGE_ERROR, "Invalid id", request, router);
            return router;
        }
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        try {
            if (LifeHackValidator.validateCategory(newCategory)) {
                long lifeHackId = Long.valueOf(id);
                Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
                if (lifeHackOptional.isPresent()) {
                    lifeHack = lifeHackOptional.get();
                    lifeHackService.editLifeHackCategory(newCategory, lifeHack);
                    router.setPagePath(PagePath.PATH_PAGE_EDIT_LIFEHACK);
                    router.setRedirectRoute();
                } else {
                    setRouterConfiguration(PagePath.PATH_PAGE_ERROR, "Can't found lifehack", request, router);
                }
            } else {
                setRouterConfiguration(path, "Incorrect category", request, router);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }

    private void setRouterConfiguration(String path, String message, HttpServletRequest request, Router router) {
        request.getSession().setAttribute(CommandConstant.MESSAGE, message);
        router.setPagePath(path);
        router.setRedirectRoute();
    }
}
