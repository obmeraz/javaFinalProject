package by.zarembo.project.command;

import by.zarembo.project.controller.Router;
import by.zarembo.project.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
