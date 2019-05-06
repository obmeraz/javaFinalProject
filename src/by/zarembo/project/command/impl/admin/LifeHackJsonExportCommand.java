package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LifeHackJsonExportCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        int lifeHackId = Integer.parseInt(request.getParameter("id"));
        LifeHack lifeHack;
        try {
            Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
            if (lifeHackOptional.isPresent()) {
                lifeHack = lifeHackOptional.get();
                String json = lifeHackService.exportToJson(lifeHack);
                router.setRedirectRoute();
                request.getSession().setAttribute("json", json);
                router.setPagePath("/jsp/lifehack/export.jsp");
            } else {
                request.getSession().setAttribute("message", "incorrect id");
                router.setRedirectRoute();
                router.setPagePath(PagePath.PATH_PAGE_ERROR);
            }

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
