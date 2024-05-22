package org.example;

import io.javalin.Javalin;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.redoc.ReDocPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import org.example.controler.UserController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {

        UserController userControler = new UserController();
        Javalin.create(config -> {
            config.registerPlugin(new OpenApiPlugin(pluginConfig -> {
                pluginConfig.withDefinitionConfiguration((version, definition) -> {
                    definition.withOpenApiInfo(info -> info.setTitle("Javalin OpenAPI example"));
                });
            }));
            config.registerPlugin(new SwaggerPlugin());
            config.registerPlugin(new ReDocPlugin());
            config.router.apiBuilder(() -> {
                    path("/api/v1/", () -> {
                            get("getAllUsers", userControler::getAllUser);
                            post("addUser", userControler::addUser);
                            get("exist", userControler::existUser);
                    });
                });
        }).start(8080);

        System.out.println("Hello world!");
    }
}