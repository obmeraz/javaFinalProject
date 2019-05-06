package by.zarembo.project.command.impl.common;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindLifeHacksCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        String searchCriteria = request.getParameter("searchCriteria");
        if (searchCriteria.isEmpty()) {
            router.setPagePath(path);
            router.setRedirectRoute();
            return router;
        }
        try {
            List<LifeHack> lifeHacks = lifeHackService.findByCriteria(searchCriteria);
            if (lifeHacks.isEmpty()) {
                request.setAttribute("searchMessage", "Not found");
            }
            request.setAttribute("lifehacks", lifeHacks);
            request.setAttribute("criteria", searchCriteria);
            request.setAttribute("type", "search");
            router.setPagePath(PagePath.PATH_PAGE_DISPLAY_LIFEHACKS);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
