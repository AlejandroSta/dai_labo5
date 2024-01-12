package rest.api;

import io.javalin.*;
import io.javalin.plugin.bundled.CorsPluginConfig;
import rest.api.helpers.*;

import static rest.api.helpers.Constants.*;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
                    config.plugins.enableCors(cors -> {
                        cors.add(CorsPluginConfig::anyHost);
                    });
                })
                .start(5000);

        PostgresqlJDBC jdbc = new PostgresqlJDBC(DB_URL, DB_USER, DB_PASSWORD);
        DbCtrl dbCtrl = new DbCtrl(jdbc);
        //app.get("/api/users", dbCtrl::getAll);

        app.get(API_ROOT + MSG_LIST_API[0], dbCtrl::listEndpoints);
        app.get(API_ROOT + MSG_LIST_API[2], dbCtrl::connect);
        app.post(API_ROOT + MSG_LIST_API[4], dbCtrl::loginUser);
        app.put(API_ROOT + MSG_LIST_API[6], dbCtrl::createUser);
        app.patch(API_ROOT + MSG_LIST_API[8], dbCtrl::updatePassword);
        app.delete(API_ROOT + MSG_LIST_API[10], dbCtrl::deleteUser);
        System.out.println("Launched API!");
    }
}