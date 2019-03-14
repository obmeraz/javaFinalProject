package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DisplayLifeHacksListCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        List<LifeHack> lifeHacks;
        Router router = new Router();
        try {
            lifeHacks = lifeHackService.takeLifeHacksList();
            if (lifeHacks.isEmpty()) {
                request.setAttribute(AttributeConstant.MESSAGE, "Not any lifehacks yet");
            }
            router.setPagePath(PagePath.PATH_PAGE_ADMIN);
            request.setAttribute(AttributeConstant.LIFEHACKS, lifeHacks);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
