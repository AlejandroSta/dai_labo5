package rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.HashMap;


import rest.api.helpers.PostgresqlJDBC;
import static rest.api.helpers.Constantes.*;

class DbCtrl {
    private final PostgresqlJDBC jdbc;

    public DbCtrl(PostgresqlJDBC jdbc) {
        this.jdbc = jdbc;
    }

    public void listEndpoints(Context ctx) {
        try {
            HashMap<String, String> t = new HashMap<>();
            for(int i = 0; i+1 < MSG_LIST_API.length; i+=2){
                t.put(MSG_LIST_API[i], MSG_LIST_API[i+1]);
            }

            ctx.json(toJson(t));
        } catch (JsonProcessingException e) {
            System.out.println(MSG_ERROR_JSON_PROCESSING+
                    " Line : "+ e.getStackTrace()[12].getLineNumber()+
                    ", "+getClass().getSimpleName());
        }
    }

    public void connect(Context ctx) {
        /*int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(users.get(id));*/
        try {
            jdbc.connect();

            try {
                ctx.json(jsonMessage(MSG_DB_HERE));
            } catch (JsonProcessingException e) {
                System.out.println(MSG_ERROR_JSON_PROCESSING+
                        " Line : "+ e.getStackTrace()[12].getLineNumber()+
                        ", "+getClass().getSimpleName());
            }
        } catch (SQLException ex) {
            try {
                ctx.json(jsonMessage(MSG_DB_NOT_HERE+"\\"));
            } catch (JsonProcessingException e) {
                System.out.println(MSG_ERROR_JSON_PROCESSING+
                        " Line : "+ e.getStackTrace()[12].getLineNumber()+
                        ", "+getClass().getSimpleName());
            }
        }
    }

    private JsonNode jsonMessage(String message) throws JsonProcessingException {
        HashMap<String, String> t = new HashMap<>();
        t.put("message", message);
        return toJson(t);
    }

    private JsonNode toJson(HashMap<String, String> values) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder("{");

        boolean isFirstEl = true;
        for (String key : values.keySet()) {
            if (!isFirstEl) sb.append(",");
            sb.append("\"")
                    .append(key)
                    .append("\":\"")
                    .append(values.get(key))
                    .append("\"");
            isFirstEl = false;
        }

        sb.append("}");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(sb.toString());
    }
}
