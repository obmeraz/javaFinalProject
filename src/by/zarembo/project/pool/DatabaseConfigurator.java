package by.zarembo.project.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Database configurator.
 */
class DatabaseConfigurator {
    private static Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_FILENAME = "database.properties";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER_NAME = "db.user";
    private static final String DATABASE_USER_PASSWORD = "db.password";

    /**
     * Gets connection from database.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILENAME);
        if (propertiesFile == null) {
            logger.fatal("Properties is missing in classpath");
            throw new RuntimeException("Properties is missing in classpath");
        }
        try {
            properties.load(propertiesFile);
        } catch (IOException e) {
            logger.fatal("Database properties file doesn't load", e);
            throw new RuntimeException("Database properties file doesn't load");
        } finally {
            try {
                propertiesFile.close();
            } catch (IOException e) {
                logger.error("Can't close input stream", e);
            }
        }
        if (!properties.containsKey(DATABASE_URL) || !properties.containsKey(DATABASE_USER_NAME)
                || !properties.containsKey(DATABASE_USER_PASSWORD)) {
            logger.fatal("Database properties file hasn't right properties");
            throw new RuntimeException("Database properties file hasn't right properties");
        }
        String url = properties.getProperty(DATABASE_URL);
        String user = properties.getProperty(DATABASE_USER_NAME);
        String pass = properties.getProperty(DATABASE_USER_PASSWORD);
        return DriverManager.getConnection(url, user, pass);
    }
}

