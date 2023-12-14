package rest.api;

import io.javalin.http.Context;

import java.util.concurrent.ConcurrentHashMap;

class UserController {
    private ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<java.lang.Integer, User>();
    private int lastId = 0;

    public UserController() {
        users.put(lastId, new User("Anita", "Braig"));
        users.put(lastId, new User("Bill", "Ding"));
        users.put(lastId, new User("Chris P.P.", "Bacon"));
    }

    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(users.get(id));
    }
}
