package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;
import by.zarembo.project.util.LifeHackValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EditLifeHackContentCommand implements Command {
    private static final String NEW_LIFEHACK_CONTENT = "new_content";
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        LifeHack lifeHack;
        String newContent = request.getParameter(NEW_LIFEHACK_CONTENT);
        String path = String.valueOf(request.getSession().getAttribute(AttributeConstant.CURRENT_PAGE));
        String id = request.getParameter(AttributeConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            setRouterConfiguration(PagePath.PATH_PAGE_ERROR, "Invalid lifehackId", request, router);
            return router;
        }
        try {
            if (LifeHackValidator.validateContent(newContent)) {
                long lifeHackId = Long.valueOf(id);
                Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
                if (lifeHackOptional.isPresent()) {
                    lifeHack = lifeHackOptional.get();
                    lifeHackService.editLifeHackContent(newContent, lifeHack);
                    router.setPagePath(PagePath.PATH_PAGE_EDIT_LIFEHACK);
                    router.setRedirectRoute();
                } else {
                    setRouterConfiguration(PagePath.PATH_PAGE_ERROR, "Can't found lifehack", request, router);
                }
            } else {
                setRouterConfiguration(path, "Invalid lifehack content", request, router);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }

    private void setRouterConfiguration(String path, String message, HttpServletRequest request, Router router) {
        request.getSession().setAttribute(AttributeConstant.MESSAGE, message);
        router.setPagePath(path);
        router.setRedirectRoute();
    }
}
