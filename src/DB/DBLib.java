package DB;

import UI.Message;
import sun.awt.Mutex;

import java.util.LinkedList;
import java.util.List;

import java.sql.*;
import java.util.concurrent.Semaphore;

/**
 * Created by onotole on 02.04.16.
 */
public class DBLib {

    private String defaultDBName = "testdb.sqlite";
    private String dbName = defaultDBName;
    private Semaphore semaphore = new Semaphore(2);
    private Connection con = null;
    private Statement stmt = null;

    public void DBLib(String dbName) {
        this.dbName = dbName;
    }

    public void DBLib() {
        this.dbName = defaultDBName;
    }

    private boolean initConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            stmt = con.createStatement();

        } catch (SQLException se) {
            System.out.println("Caught SQL ERROR: " + se.toString());
            return false;
        } catch (ClassNotFoundException ce) {
            System.out.println("Caught ERROR: " + ce.toString());
            return false;
        }
        return true;
    }

    public void closeConnection() {
        try {
            stmt.close();
            con.close();
        } catch (SQLException se) {
            System.out.println("ERROR while close connect: " + se.getMessage());
        } finally {
            con = null;
            stmt = null;
        }
    }

    private ResultSet doSQLRequest(String request) {
        System.out.println("Request: " + request);
        if (con == null || stmt == null) {
            initConnection();
        }
        ResultSet rs = null;
        try {
//            semaphore.acquire();
            rs = stmt.executeQuery(request);

        } catch (SQLException se) {
            System.out.println("SQL EXCEPTION: " + se.toString());
            if (se.getErrorCode() != 101)
                System.exit(1);
        }
//        } catch ( InterruptedException ie ) {
//            System.out.println(ie.getStackTrace());
//        } finally {
////            semaphore.release();
//        }
        return rs;
    }

    public void createDB() {
        doSQLRequest(sqlReqeuests.CREATE_CHAT_DB);
    }

    public void dropDB() {
        doSQLRequest(sqlReqeuests.DROP_CHAT_DB);
    }

    public void cleanDB() {
        doSQLRequest(sqlReqeuests.DELETE_ALL_CHAT_DB);
    }

    public int getLastMessageID() {
        int result = 0;
        try {
            result = doSQLRequest(sqlReqeuests.GET_LAST_MESSAGE_ID).getInt("ID");
        } catch (SQLException se) {
            System.out.println("Coudn't eject last message id: " + se.getMessage());
        }
        return result;
    }

    public int addMessage(String nickname, String message) {
        String request = sqlReqeuests.INSERT_MESSAGE_PART1 + nickname + sqlReqeuests.INSERT_MESSAGE_PART2 + message +
                sqlReqeuests.INSERT_MESSAGE_PART3;

        doSQLRequest(request);
        return getLastMessageID();
    }

    public List<Message> getMessageAfterID(int ID) {
        ResultSet rs = doSQLRequest(sqlReqeuests.SELECT_ALL_MESSAGES_AFTER_ID_PART1 + ID +
                sqlReqeuests.SELECT_ALL_MESSAGES_AFTER_ID_PART2);
        List<Message> list = new LinkedList<>();
        try {
            while (rs.next()) {
                Message message = new Message();
                message.setNickname(rs.getString("nickname"));
                message.setMessage(rs.getString("message"));
                list.add(message);
            }
        } catch (SQLException se) {
            System.out.println("Caugth ERROR while ejecting messages: " + se.toString());
            System.exit(1);
        }

        return list;
    }


}
