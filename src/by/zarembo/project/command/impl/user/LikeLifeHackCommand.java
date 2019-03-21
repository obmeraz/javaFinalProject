package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;
import by.zarembo.project.util.LifeHackValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Like lifehack command.
 */
public class LikeLifeHackCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        LifeHack lifeHack;
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        User user = (User) request.getSession().getAttribute(CommandConstant.USER);
        String id = request.getParameter(CommandConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Invalid lifehack id");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        try {
            long lifeHackId = Long.parseLong(id);
            if (!lifeHackService.isLifeHackLikedAlready(lifeHackId, user.getUserId())) {
                lifeHackService.likeLifeHack(lifeHackId);
                lifeHackService.insertUserLikeLifeHack(lifeHackId, user.getUserId());
            } else {
                lifeHackService.removeLikeLifeHack(lifeHackId);
                lifeHackService.deleteUserLikeLifeHack(lifeHackId, user.getUserId());
            }
            Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
            if (lifeHackOptional.isPresent()) {
                lifeHack = lifeHackOptional.get();
                int likesAmount = lifeHack.getLikesAmount();
                String commentLikes = String.valueOf(likesAmount);
                router.setAjaxRoute(commentLikes);
            } else {
                router.setPagePath(path);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

