package rest.api.helpers;

public class Constantes {
    public final static String DB_URL = "jdbc:postgresql://localhost:3306/db_app";
    public final static String DB_USER = "u_app";
    public final static String DB_PASSWORD = "super_secret";

    public final static String API_ROOT = "/api";

    public final static String MSG_DB_NOT_HERE = "Oh oh, db not here!";
    public final static String MSG_DB_HERE = "Connected to Db !";
    public final static String[] MSG_LIST_API = {"/", "List all endpoints.",
                                                 "/connect", "Connect to DB and give a feedback"};
    public final static String MSG_ERROR_JSON_PROCESSING = "Json not parse.";

}
