package by.zarembo.project.controller;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandFactory;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.command.impl.EmptyCommand;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/controller")
@MultipartConfig(location = "d:/tmp",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {
    //todo don't forget uncomment pattern in html
    //todo how test service if there are almost all methods with DB?only util
    //todo what jautodoc plugin for eclipse or maybe idea
    //todo need we text document on interview?need,only description and use cases
    private static final String COMMAND_PARAMETER = "command";
    private static final String EXCEPTION_PARAMETER = "exception";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Command> commandOptional = CommandFactory.defineCommand(request.getParameter(COMMAND_PARAMETER));
        Command command = commandOptional.orElse(new EmptyCommand());
        Router router = new Router();
        try {
            router = command.execute(request);
            switch (router.getRoute()) {
                case FORWARD:
                    RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                    dispatcher.forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(request.getContextPath() + router.getPagePath());
                    break;
                case AJAX:
                    response.getWriter().write(router.getAjaxAnswer());
                    break;
            }
        } catch (CommandException e) {
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            request.getSession().setAttribute(EXCEPTION_PARAMETER, e);
            response.sendRedirect(request.getContextPath() + router.getPagePath());
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
    }
}
