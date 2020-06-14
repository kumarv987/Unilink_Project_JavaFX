package model.hsql_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLJdbcAdaptor {
    private static SQLJdbcAdaptor sqlJdbcAdaptor = null;
    private String DB_Name = "unilink-db";
    public static SQLJdbcAdaptor getInstance() {
        if (sqlJdbcAdaptor == null) {
            sqlJdbcAdaptor = new SQLJdbcAdaptor();
        }
        return sqlJdbcAdaptor;
    }

    public Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "vijit", "");
        return con;
    }

    public void initTables()  {


        // Create 5 tables
        try(Connection connection = getConnection(DB_Name);
            Statement statement = connection.createStatement()
        ) {

            statement.executeUpdate("DROP TABLE job");
            statement.executeUpdate("DROP TABLE sale");
            statement.executeUpdate("DROP TABLE event");
            statement.executeUpdate("DROP TABLE posts");
            statement.executeUpdate("DROP TABLE user");

            //            statement.executeUpdate("DROP TABLE event");
            statement.executeUpdate("CREATE TABLE user ("
                                    + "userID VARCHAR(20) NOT NULL,"
                                    + "PRIMARY KEY (userID))");
            statement.executeUpdate("CREATE TABLE posts ("
                                    +"postID VARCHAR(20) NOT NULL AUTO_INCREMENT,"
                                    +"title VARCHAR(20) NOT NULL,"
                                    +"description VARCHAR(80),"
                                    +"status VARCHAR(10) NOT NULL,"
                                    +"photo VARCHAR(50),"
                                    +"creatorID VARCHAR(20) NOT NULL,"
                                    +"PRIMARY KEY (postID),"
                                    +"FOREIGN KEY (creatorID) REFERENCES user(userID))");
            statement.executeUpdate("CREATE TABLE event ("
                                    +"postID VARCHAR(20) NOT NULL,"
                                    +"venue VARCHAR(20) NOT NULL,"
                                    +"date VARCHAR(20) NOT NULL,"
                                    +"capacity VARCHAR(20) NOT NULL,"
                                    +"attCount VARCHAR(20) NOT NULL,"
                                    +"eventId VARCHAR(10) NOT NULL,"
                                    +"PRIMARY KEY (eventId)," +
                    "FOREIGN KEY (postID) REFERENCES posts(postID))");
            statement.executeUpdate("CREATE TABLE sale ("
                    +"postID VARCHAR(20) NOT NULL,"
                    +"askPrice VARCHAR(20) NOT NULL,"
                    +"highOffer VARCHAR(20) NOT NULL,"
                    +"minRaise VARCHAR(20) NOT NULL,"
                    +"saleId VARCHAR(20) NOT NULL,"
                    +"PRIMARY KEY (saleId),"
                    +"FOREIGN KEY (postID) REFERENCES posts(postID))");
            statement.executeUpdate("CREATE TABLE job ("
                    +"postID VARCHAR(20) NOT NULL,"
                    +"jobId VARCHAR(20) NOT NULL,"
                    +"propPrice VARCHAR(20) NOT NULL,"
                    +"lowOffer VARCHAR(20) NOT NULL,"
                    +"PRIMARY KEY (jobId),"
                    +"FOREIGN KEY (postID) REFERENCES posts(postID))");
            statement.executeUpdate("CREATE TABLE reply ("
                    +"replyId VARCHAR(20) NOT NULL,"
                    +"postId VARCHAR(20) NOT NULL,"
                    +"respId VARCHAR(20) NOT NULL,"
                    + "offerValue FLOAT(20),"
                    +"PRIMARY KEY (replyId),"
                    +"FOREIGN KEY (postID) REFERENCES posts(postID),"
                    +"FOREIGN KEY (respId) REFERENCES user(userID))");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<List<String>> executeQuery(String sqlStatement) {
        List<List<String>> resultList = new ArrayList<>();

        // Execute query

        // Prepare result List

        return resultList;
    }

    public boolean insertValue(String query) {
        boolean status = false;
        // Create statements

        // Create 5 tables
        try(Connection connection = getConnection(DB_Name);
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
            connection.commit();

        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return status;
    }

}
