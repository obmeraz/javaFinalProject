package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.RoleType;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;
import by.zarembo.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowStatisticsCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            List<LifeHack> lifeHacks = lifeHackService.takeAllLifeHacksList();
            List<User> users = userService.takeAllUsers();
            int allUsersNumber = users.size();
            int allLifeHacksNumber = lifeHacks.size();
            List<LifeHack> popularLifeHacks = lifeHacks
                    .stream()
                    .sorted(Comparator.comparingInt(LifeHack::getLikesAmount)).limit(5)
                    .collect(Collectors.toList());
            List<User> adminUsers = users
                    .stream()
                    .filter(user -> RoleType.ADMIN.equals(user.getRole()))
                    .collect(Collectors.toList());
            List<LifeHack> freshestLifeHacks = lifeHacks
                    .stream()
                    .sorted(Comparator.comparingLong(LifeHack::getPublicationDate))
                    .limit(5)
                    .collect(Collectors.toList());

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
