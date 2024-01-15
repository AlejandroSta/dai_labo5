package rest.api.helpers;

public class Constants {
    public final static String DB_URL = "jdbc:postgresql://localhost:3306/db_app";
    public final static String DB_USER = "u_app";
    public final static String DB_PASSWORD = "super_secret";

    public final static String API_ROOT = "";

    public final static String MSG_DB_NOT_HERE = "Oh oh, db not here!";
    public final static String MSG_DB_HERE = "Connected to Db !";
    public final static String[] MSG_LIST_API = {
            "/", "List all endpoints.",
            "/connect", "Connect to db",
            "/loginUser", "Tries to login with a username and password",
            "/createUser", "Add a user to the database",
            "/updatePassword", "Update the user password",
            "/deleteUser", "Tries to delete a user given its username"
    };
    public final static String MSG_ERROR_JSON_PROCESSING = "Json not parse.";
    public final static String RQ_GET_USER = "SELECT password FROM _user WHERE username=?;";
    public final static String RQ_CREATE_USER = "INSERT INTO _user VALUES (?, ?);";
    public final static String RQ_UPDATE_USER = "UPDATE _user SET password=? WHERE username=?;";
    public final static String RQ_DELETE_USER = "DELETE FROM _user WHERE username=?;";

}
