package rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


import rest.api.helpers.PostgresqlJDBC;

import static rest.api.helpers.Constants.*;

public class DbCtrl {
    private boolean isCreated = false;
    private final PostgresqlJDBC jdbc;
    private String loggedUser;

    public DbCtrl(PostgresqlJDBC jdbc) {
        this.jdbc = jdbc;
        connect();
    }

    public void listEndpoints(Context ctx) {
        try {
            HashMap<String, String> t = new HashMap<>();
            for (int i = 0; i + 1 < MSG_LIST_API.length; i += 2) {
                t.put(MSG_LIST_API[i], MSG_LIST_API[i + 1]);
            }

            ctx.json(toJson(t));
        } catch (JsonProcessingException e) {
            System.out.println(MSG_ERROR_JSON_PROCESSING +
                    " Line : " + e.getStackTrace()[12].getLineNumber() +
                    ", " + getClass().getSimpleName());
        }
    }

    public boolean connect() {
        try {
            jdbc.connect();
            return jdbc.isConnected();
        } catch (SQLException ignored) {
            return false;
        }
    }

    public void connect(Context ctx) {
        ctx.json(connect());
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

    private HashMap<String, String> jsonStringToHashMap(String s) {
        HashMap<String, String> h = new HashMap<>();

        s = s.replace("{","");
        s = s.replace("}","");
        s = s.replace(" ","");
        s = s.replace("\"","");

        for(String line : s.split(",")) {
            h.put(line.split(":")[0], line.split(":")[1]);
        }

        return h;
    }

    public void createUser(Context ctx){
        try {
            PreparedStatement ps = jdbc.getPreparedStatement(RQ_CREATE_USER);
            HashMap<String, String> json = jsonStringToHashMap(ctx.body());
            ps.setString(1, json.get("username"));
            ps.setString(2, json.get("password"));
            int r = jdbc.CUD(ps);
            if(r != 1) throw new SQLException();
            ctx.status(200);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                ctx.json(jsonMessage(e.getMessage()));
            } catch (Exception ignored){}
            ctx.status(500);
        }
    }

    public void loginUser(Context ctx) {
        try {
            PreparedStatement ps = jdbc.getPreparedStatement(RQ_GET_USER);
            HashMap<String, String> json = jsonStringToHashMap(ctx.body());
            ps.setString(1, json.get("username"));
            ResultSet r = jdbc.R(ps);
            r.next();
            if(r.getString(1).equals(json.get("password"))) ctx.status(200);
            else ctx.status(403);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                ctx.json(jsonMessage(e.getMessage()));
            } catch (Exception ignored){}
            ctx.status(500);
        }
    }

    public void updatePassword(Context ctx) {
        try {
            PreparedStatement ps = jdbc.getPreparedStatement(RQ_UPDATE_USER);
            HashMap<String, String> json = jsonStringToHashMap(ctx.body());
            ps.setString(1, json.get("password"));
            ps.setString(2, json.get("username"));
            int r = jdbc.CUD(ps);
            if(r != 1) throw new SQLException();
            ctx.status(200);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                ctx.json(jsonMessage(e.getMessage()));
            } catch (Exception ignored){}
            ctx.status(500);
        }
    }

    public void deleteUser(Context ctx) {
        try {
            PreparedStatement ps = jdbc.getPreparedStatement(RQ_DELETE_USER);
            HashMap<String, String> json = jsonStringToHashMap(ctx.body());
            ps.setString(1, json.get("username"));
            int r = jdbc.CUD(ps);
            if(r != 1) throw new SQLException();
            ctx.status(200);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                ctx.cookie("message", e.getMessage());
            } catch (Exception ignored){}
            ctx.status(500);
        }
    }
}
