package bdr.projet.helpers;

import java.sql.*;
import java.util.Properties;

/**
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

public class PostgesqlJDBC {

    Connection db;
    Properties props;
    String url;

    public PostgesqlJDBC(String url, String username, String password) {
        props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        this.url = url;
    }

    public void connect() throws SQLException {
        db = DriverManager.getConnection(url, props);
    }

    public boolean isConnect(){
        return db != null;
    }

    public PreparedStatement getPrepareStatement(String request) throws SQLException { //could do variable parameter but would complexity the code for nothing
        return db.prepareStatement(request);
    }

    public ResultSet R(PreparedStatement request) throws SQLException {
        ResultSet rs = request.executeQuery();

        /*while (rs.next()) {
            System.out.print("Column 1 returned "); //TODO RESOLVE THAT ISSUE
            System.out.println(rs.getString(1));
        }

        rs.close();
        request.close();*/
        return  rs;
    }

    public int CUD(PreparedStatement request) throws SQLException {
        int res = request.executeUpdate();
        request.close();
        return res;
    }

    public boolean LDD(PreparedStatement request) throws SQLException {
        boolean res = request.execute();
        request.close();
        return res;
    }

    public void disconnect() throws SQLException {
        db.close();
        db = null;
    }
}
