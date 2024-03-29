package rest.api.helpers;

import java.sql.*;
import java.util.Properties;

/*
 *
 *private void fetchAll() {
 *         fetchGames();
 *         fetchMods();
 *     }
 *
 *     private void fetchGames() {
 *         if(!needToFetchGames) return;
 *
 *         try {
 *             PreparedStatement request = jdbc.getPrepareStatement(DB_RQ_GET_GAMES);
 *             ResultSet rs = jdbc.R(request);
 *
 *             while (rs.next()) {
 *                 String gameName = rs.getString(1);
 *                 if (getGame(gameName) != null) continue; //TODO OR UPDATE
 *                 games.add(new Game(gameName));
 *             }
 *
 *             rs.close();
 *             request.close();
 *             needToFetchGames = false;
 *         } catch (SQLException ignored) {
 *         }
 *     }
 *
 *     private void fetchMods() {
 *         //Test function. TODO mod class and changes this function next
 *         if(!needToFetchMods) return;
 *         fetchGames();
 *
 *         try {
 *             PreparedStatement request = jdbc.getPrepareStatement(DB_RQ_GET_MODS);
 *             ResultSet rs = jdbc.R(request);
 *
 *             while (rs.next()) { //TODO DEBUG
 *                 mods.add(new Mod(rs.getString(2), getGame(rs.getString(1))));
 *             }
 *
 *             rs.close();
 *             request.close();
 *             needToFetchMods = false;
 *         } catch (SQLException ignored) {
 *         }
 *     }
 *
 *
 */

public class PostgresqlJDBC {

    Connection db;
    Properties props;
    String url;

    public PostgresqlJDBC(String url, String username, String password) {
        props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        this.url = url;
    }

    public void connect() throws SQLException {
        db = DriverManager.getConnection(url, props);
    }

    public boolean isConnected() {
        return db != null;
    }

    public PreparedStatement getPreparedStatement(String request) throws SQLException { //could do variable parameter but would complexity the code for nothing
        return db.prepareStatement(request);
    }

    public ResultSet R(PreparedStatement request) throws SQLException {
        return request.executeQuery();
    }

    public int CUD(PreparedStatement request) throws SQLException {
        int res = request.executeUpdate();
        request.close();
        return res;
    }

    public boolean LDD(PreparedStatement request) throws SQLException {
        boolean res = request.execute();
        System.out.println("not here");
        request.close();
        return res;
    }

    public void disconnect() throws SQLException {
        db.close();
        db = null;
    }
}
