package rest.api;

import io.javalin.*;
import io.javalin.plugin.bundled.CorsPluginConfig;
import rest.api.helpers.*;

import static rest.api.helpers.Constantes.*;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
                    config.plugins.enableCors(cors -> {
                        cors.add(CorsPluginConfig::anyHost);
                    });
                })
                .start(5000);

        PostgesqlJDBC jdbc = new PostgesqlJDBC(DB_URL, DB_USER, DB_PASSWORD);
        DbCtrl dbCtrl = new DbCtrl(jdbc);
        //app.get("/api/users", dbCtrl::getAll);

        app.get(API_ROOT + MSG_LIST_API[0], dbCtrl::listEndpoints);
        app.get(API_ROOT + MSG_LIST_API[2], dbCtrl::connect);
        /*app.post("/api/users/", dbCtrl::create);
        app.put("/api/users/{id}", dbCtrl::update);
        app.delete("/api/users/{id}", dbCtrl::delete);*/
        System.out.println("Launched API!");
    }
}