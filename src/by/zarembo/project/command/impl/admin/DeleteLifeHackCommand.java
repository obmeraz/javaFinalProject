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
import java.util.List;
import java.util.Optional;

public class DeleteLifeHackCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(AttributeConstant.CURRENT_PAGE));
        String id = request.getParameter(AttributeConstant.LIFEHACK_ID);
        try {
            if (LifeHackValidator.validateId(id)) {
                long lifeHackId = Long.parseLong(id);
                Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
                if (lifeHackOptional.isPresent()) {
                    lifeHackService.deleteLifeHack(lifeHackId);
                    router.setPagePath(path);
                    router.setRedirectRoute();
                }else{
                    request.getSession().setAttribute(AttributeConstant.MESSAGE, "Doesn't exist");
                    router.setPagePath(PagePath.PATH_PAGE_ERROR);
                    router.setRedirectRoute();
                }
            } else {
                request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid id");
                router.setPagePath(PagePath.PATH_PAGE_ERROR);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
