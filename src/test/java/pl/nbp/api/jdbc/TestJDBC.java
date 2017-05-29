package pl.nbp.api.jdbc;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJDBC {

    public static final Logger logger = Logger.getLogger(TestJDBC.class);

    @Test
    public void testJDBC() {
        logger.info("-------- HSQL " + "JDBC Connection Testing ------------");
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            logger.info("Where is your HSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;
        }
        logger.info("HSQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
        } catch (SQLException e) {
            logger.info("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            logger.info("You made it, take control your database now!");
        } else {
            logger.info("Failed to make connection!");
        }
    }


}
