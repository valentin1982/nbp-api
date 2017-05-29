package pl.nbp.api.database;

import org.apache.log4j.Logger;
import pl.nbp.api.domain.Rate;
import pl.nbp.api.util.date.DateHelperUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DBService {

    private static final Logger logger = Logger.getLogger(DBService.class);

    private static int count = 0;

    private Connection conn = null;

    private PreparedStatement stmt = null;

    private Statement statement = null;

    private ResultSet rs = null;

    /**
     * Add data to database
     *
     * @param effectivedate
     * @param mid
     * @throws Exception
     */
    public void addAll(LocalDate effectivedate, Double mid) throws Exception {
        count++;
        String query = "INSERT INTO exchangeratesseries " + "(effectivedate,mid)" + "VALUES (?,?)";
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, String.valueOf(effectivedate));
            stmt.setString(2, String.valueOf(mid));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);

        }
        logger.info(DateHelperUtil.LOCAL_DATE + " " + " inserted to the database " + count + " new values ");
    }

    /**
     * show data from db
     *
     * @return
     * @throws Exception
     */
    public List<Rate> getData() throws Exception {
        List<Rate> rates = new ArrayList<>();
        String query = "SELECT * FROM exchangeratesseries ORDER BY effectivedate DESC";
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.createStatement();

            rs = statement.executeQuery(query);
            while (rs.next()) {
                Rate rate = new Rate();
                rate.setEffectivedate(LocalDate.parse(rs.getString("effectivedate")));
                rate.setMid(rs.getDouble("mid"));
                rates.add(rate);
            }
        } finally {
            ConnectionFactory.closeConnection(conn, statement, rs);
        }
        return rates;
    }

}
