package DB;

/**
 * Created by onotole on 02.04.16.
 */
public class sqlReqeuests {
    private static final String CHAT_DB_NAME = "chat_data";

    final static public String CREATE_CHAT_DB = "create table if not exists " + CHAT_DB_NAME + " " +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nickname CHAR(25), " +
            "message CHAR(300));";
    final static public String SELECT_ALL_MESSAGES = "select * from " + CHAT_DB_NAME + ";";
    final static public String SELECT_ALL_MESSAGES_AFTER_ID_PART1 = "select * from " + CHAT_DB_NAME  + " where ID > ";
    final static public String SELECT_ALL_MESSAGES_AFTER_ID_PART2 = ";";
    final static public String GET_LAST_MESSAGE_ID = "select ID from " + CHAT_DB_NAME + " ORDER BY ID DESC LIMIT 1;";
    final static public String DROP_CHAT_DB = "drop table " + CHAT_DB_NAME + ";";
    final static public String DELETE_ALL_CHAT_DB = "delete from " + CHAT_DB_NAME + ";";
    final static public String INSERT_MESSAGE_PART1 = "insert into " + CHAT_DB_NAME + " (nickname, message) values (\"";
    final static public String INSERT_MESSAGE_PART2 = "\",\"";
    final static public String INSERT_MESSAGE_PART3 = "\");";

}
