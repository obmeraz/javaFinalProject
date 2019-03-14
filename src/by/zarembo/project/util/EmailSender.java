package by.zarembo.project.util;

import by.zarembo.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class EmailSender {
    private static final String PROPERTIES_FILENAME = "mail.properties";
    private static final String EMAIL_ADDRESS = "ml.address";
    private static final String EMAIL_PASSWORD = "ml.password";
    private static final String EMAIL_MESSAGE_TYPE = "text/html; charset=utf-8";
    private static final String EMAIL_SUBJECT = "LifeHacker";
    private static Logger logger = LogManager.getLogger();

    public void sendEmail(User user, EmailType emailType) {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILENAME);
        if (propertiesFile == null) {
            logger.error("Mail properties is missing in classpath");
        }
        try {
            properties.load(propertiesFile);
        } catch (IOException e) {
            logger.error("Mail properties file doesn't load", e);
        } finally {
            try {
                if (propertiesFile != null) {
                    propertiesFile.close();
                }
            } catch (IOException e) {
                logger.error("Can't close input stream", e);
            }
        }

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        String msg = null;
        try {
            String emailAddress = properties.getProperty(EMAIL_ADDRESS);
            message.setFrom(new InternetAddress(emailAddress));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            switch (emailType) {
                case SIGN_UP:
                    String hash = user.getPassword();
                    String address = "http://localhost:8080/LifehackWeb_war_exploded/controller?command=activate_account&hash=";
                    msg = "<a href=\"" + address + hash + "\">here</a>";
                    break;
                case CHANGE_EMAIL:
                    msg = "Your email was changed";
            }
            String emailPassword = properties.getProperty(EMAIL_PASSWORD);
            message.setSubject(EMAIL_SUBJECT);
            message.setContent(msg, EMAIL_MESSAGE_TYPE);
            Transport transport = mailSession.getTransport();
            transport.connect(null, emailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            logger.error("Error inter the gmail", e);
        }
    }
}

