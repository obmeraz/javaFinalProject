package by.zarembo.project.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final int POOL_SIZE = 16;
    private static Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> usedConnections;
    private static AtomicBoolean create = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private ConnectionPool() {
        registerDatabaseDriver();
        init();
        startCheckingThread();
    }

    private void registerDatabaseDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            logger.fatal("Can't register mysql jdbc driver", e);
            throw new RuntimeException("Can't register mysql jdbc driver", e);
        }
    }

    private void init() {
        availableConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        usedConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                availableConnections.offer(new ProxyConnection(DatabaseConfigurator.getConnection()));
            } catch (SQLException e) {
                logger.error("Can't create connection", e);
            }
        }
        if (availableConnections.isEmpty()) {
            logger.fatal("No available connections");
            throw new RuntimeException("No available connections");
        }
    }

    public static ConnectionPool getInstance() {
        if (!create.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection takeConnection() {

        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            connection.setGivingConnectionTime(System.currentTimeMillis());
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.warn("Interrupted", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            usedConnections.remove(proxyConnection);
            availableConnections.offer(proxyConnection);
        }
    }

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.info(String.format("Error deregistering driver %s", driver), e);
            }
        }
    }

    public void closePool() {
        deregisterDriver();
        ProxyConnection connection;
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                connection = availableConnections.take();
                connection.realCloseConnection();
            } catch (InterruptedException e) {
                logger.warn("Interrupted", e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("Can't close connection", e);
            }
        }
    }

    private void startCheckingThread() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(() -> {
            for (ProxyConnection connection : usedConnections) {
                if (System.currentTimeMillis() - connection.getGivingConnectionTime()
                        > TimeUnit.HOURS.toMillis(2)) {
                    logger.error("Connection leak");
                }
            }
        }, 0, 3, TimeUnit.HOURS);
    }
}
