package model.hsql_db;

import controller.LoginPageController;
import controller.MainPageController;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLJdbcAdaptor {
    private static SQLJdbcAdaptor sqlJdbcAdaptor = null;
    private String DB_Name = "unilink-db";

    //Getter method
    public String getDB_Name(){
        return DB_Name;
    }

    //Setter method
    public void setDB_Name(String s){
        this.DB_Name = s;
    }

    //Singleton
    public static SQLJdbcAdaptor getInstance() {
        if (sqlJdbcAdaptor == null) {
            sqlJdbcAdaptor = new SQLJdbcAdaptor();
        }
        return sqlJdbcAdaptor;
    }

    /*******************************************************************************************************************
     * THis method checks if the connection with database was successful.
     ******************************************************************************************************************/
    public Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
        //Registering the HSQLDB JDBC Driver
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // Database file will be created in the database folder in the project.
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "vijit", "");
        return con;
    }

    /*******************************************************************************************************************
     * THis method return true if table exists, or returns false if table does not exist.
     ******************************************************************************************************************/
    public void initializeTables() {
        try(Connection connection = getConnection(DB_Name);
            Statement statement = connection.createStatement())
        {
            // Create tables
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user ("
                    + "userID VARCHAR(20) NOT NULL,"
                    + "PRIMARY KEY (userID))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS  posts ("
                    +"postOwnID INTEGER NOT NULL,"
                    +"postType VARCHAR(10) NOT NULL,"
                    +"title VARCHAR(20) NOT NULL,"
                    +"description VARCHAR(80),"
                    +"status VARCHAR(10) NOT NULL,"
                    +"photo VARCHAR(50),"
                    +"userID VARCHAR(20) NOT NULL,"
                    +"PRIMARY KEY (postOwnID),"
                    +"FOREIGN KEY (userID) REFERENCES user(userID))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS  event ("
                    +"postOwnID INTEGER NOT NULL,"
                    +"venue VARCHAR(20) NOT NULL,"
                    +"date VARCHAR(20) NOT NULL,"
                    +"capacity VARCHAR(20) NOT NULL,"
                    +"attCount VARCHAR(20) NOT NULL,"
                    +"eventId VARCHAR(10) NOT NULL,"
                    +"PRIMARY KEY (eventId),"
                    +"FOREIGN KEY (postOwnID) REFERENCES posts(postOwnID))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS  sale ("
                    +"postOwnID INTEGER NOT NULL,"
                    +"askPrice VARCHAR(20) NOT NULL,"
                    +"highOffer VARCHAR(20) NOT NULL,"
                    +"minRaise VARCHAR(20) NOT NULL,"
                    +"saleId VARCHAR(20) NOT NULL,"
                    +"PRIMARY KEY (saleId),"
                    +"FOREIGN KEY (postOwnID) REFERENCES posts(postOwnID))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS  job ("
                    +"postOwnID INTEGER NOT NULL,"
                    +"jobId VARCHAR(20) NOT NULL,"
                    +"propPrice VARCHAR(20) NOT NULL,"
                    +"lowOffer VARCHAR(20) NOT NULL,"
                    +"PRIMARY KEY (jobId),"
                    +"FOREIGN KEY (postOwnID) REFERENCES posts(postOwnID))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS  reply ("
                    +"replyId VARCHAR(20) NOT NULL,"
                    +"postOwnID INTEGER NOT NULL,"
                    +"respId VARCHAR(20) NOT NULL,"
                    +"offerValue FLOAT(20),"
                    +"PRIMARY KEY (replyId),"
                    +"FOREIGN KEY (postOwnID) REFERENCES posts(postOwnID),"
                    +"FOREIGN KEY (respId) REFERENCES user(userID))");
            // Init starting ID values
            // Init Memory data
            systemInit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
     * THis method gets the data from the database to the memory
     ******************************************************************************************************************/
    private void systemInit() throws SQLException, ClassNotFoundException {
        List<List<String>> result = executeQuery(
                "SELECT postOwnID FROM posts ORDER BY postOwnID desc LIMIT 1");
        if(result.size() > 1) {
            Post.setPostSpecificID(Integer.parseInt(result.get(1).get(0)));
        }

        result = executeQuery(
                "SELECT eventId FROM event ORDER BY eventId desc LIMIT 1");
        if(result.size() > 1) {
            int eventId = Integer.parseInt(result.get(1).get(0).substring(3));
            Event.setNumId(eventId+1);
        }

        result = executeQuery(
                "SELECT saleId FROM sale ORDER BY saleId desc LIMIT 1");
        if(result.size() > 1) {
            int saleId = Integer.parseInt(result.get(1).get(0).substring(3));
            Sale.setSaleNumId(saleId+1);
        }

        result = executeQuery(
                "SELECT jobId FROM job ORDER BY jobId desc LIMIT 1");
        if(result.size() > 1) {
            int jobId = Integer.parseInt(result.get(1).get(0).substring(3));
            Job.setJobNumId(jobId+1);
        }

        result = executeQuery(
                "SELECT replyId FROM reply ORDER BY replyId desc LIMIT 1");
        if(result.size() > 1) {
            int jobId = Integer.parseInt(result.get(1).get(0).substring(3));
            Reply.setReplyNumId(jobId+1);
        }

        // Init Mem
        result = executeQuery(
                "SELECT userID FROM user");
        if(result.size() > 1) {
            for(List<String> userName: result) {
                User user = new User(userName.get(0));
                user.getData();
                MainPageController.listOfUsers.add(user);
            }
        }
    }

    /*******************************************************************************************************************
     * THis method return a list of data from the database.
     ******************************************************************************************************************/
    public List<List<String>> executeQuery(String sqlStatement) throws SQLException, ClassNotFoundException {
        List<List<String>> resultList = new ArrayList<>();

        try (Connection connection = sqlJdbcAdaptor.getConnection(DB_Name);
             Statement statement = connection.createStatement())
        {
            try {
                System.out.println(sqlStatement);
                ResultSet resultSet = statement.executeQuery(sqlStatement);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                List<String> header = new ArrayList<>();
                // Header
                for (int i = 1; i <= columnsNumber; i++) {
                    header.add(rsmd.getColumnName(i));
                }
                // Add the header
                resultList.add(header);
                // Parsing the returned result
                while (resultSet.next()) {
                    List<String> rowList = new ArrayList<>();
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (resultSet.getString(i) != null) {
                            rowList.add(resultSet.getString(i));
                        } else {
                            rowList.add("null");
                        }
                    }
                    resultList.add(rowList);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /*******************************************************************************************************************
     * THis method inserts a query
     ******************************************************************************************************************/
    public void insertQuery(String query){
        try(Connection connection = getConnection(DB_Name);
            Statement statement = connection.createStatement()) {
            System.out.println("Query: " + query);
            statement.executeUpdate(query);
            connection.commit();
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
     * THis method return true if table exists, or returns false if table does not exist.
     ******************************************************************************************************************/
    public boolean checkIfTableExist(String table_name) throws SQLException, ClassNotFoundException {
        boolean status = false;
        try(Connection con = getConnection(DB_Name)){
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet table = dbm.getTables(null,null,table_name,null);

            if(table != null){
                if(table.next()){
                    System.out.println(table_name + "table exists");
                    status = true;
                }else{
                    System.out.println(table_name + "table does not exist");
                    status = false;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }

    /*******************************************************************************************************************
     * THis method return true if table exists, or returns false if table does not exist.
     ******************************************************************************************************************/
    public void deleteTables() throws SQLException, ClassNotFoundException {
        try(Connection connection = getConnection(DB_Name);
            Statement statement = connection.createStatement())
        {
            statement.executeUpdate("DROP TABLE reply");
            statement.executeUpdate("DROP TABLE job");
            statement.executeUpdate("DROP TABLE sale");
            statement.executeUpdate("DROP TABLE event");
            statement.executeUpdate("DROP TABLE posts");
            statement.executeUpdate("DROP TABLE user");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
