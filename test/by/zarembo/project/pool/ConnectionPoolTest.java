package by.zarembo.project.pool;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ConnectionPoolTest {

    @Test
    public void testGetInstance() {
        ConnectionPool connectionPoolOne = ConnectionPool.getInstance();
        ConnectionPool connectionPoolTwo = ConnectionPool.getInstance();
        assertEquals(connectionPoolOne,connectionPoolTwo);
    }
}