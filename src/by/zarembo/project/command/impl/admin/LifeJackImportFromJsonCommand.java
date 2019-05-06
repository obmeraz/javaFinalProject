package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class LifeJackImportFromJsonCommand implements Command {
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Part part = null;
        try {
            part = request.getPart("json");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String uploadFilePath = request.getServletContext().getRealPath("/data");
        try {
            if (part.getSubmittedFileName() != null) {
                part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = part.getSubmittedFileName();
        CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
        String path = "D:\\EpamFinal\\javaFinalProject\\out\\artifacts\\LifehackWeb_war_exploded\\data\\" + fileName;
        LifeHack lifeHack = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            lifeHack = gson.fromJson(in, LifeHack.class);
            String base64 = lifeHack.getImage();
            byte[] decodedByte = Base64.decode(base64);
            lifeHack.setImageBytes(decodedByte);
        } catch (IOException | Base64DecodingException e) {
            e.printStackTrace();
        }
        try {
            lifeHackService.addNewLifeHack(lifeHack);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        router.setPagePath(PagePath.PATH_PAGE_MAIN);
        router.setRedirectRoute();
        return router;
    }
}
