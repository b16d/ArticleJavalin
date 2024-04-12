package org.example.controler;

import io.javalin.http.Context;
import io.javalin.openapi.*;
import org.example.domain.User;
import org.example.service.UserService;
import org.jetbrains.annotations.NotNull;

public class UserController {

    private final UserService service ;
    public UserController() {
        service = new UserService();
    }

    @OpenApi(
            summary = "Get all users",
            operationId = "getAllUsers",
            path = "/api/v1/getAllUsers",
            methods = HttpMethod.GET,
            tags = {"User"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User[].class)})
            }
    )
    public void getAllUser(@NotNull Context ctx) {
        var listOfUser = service.getAllUser();

        ctx.result(listOfUser.toString());
    }

    @OpenApi(
            summary = "Add a user",
            operationId = "add user",
            path = "/api/v1/AddUser",
            methods = HttpMethod.POST,
            tags = {"User"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = User.class)}),
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User[].class)})
            }
    )
    public void addUser(@NotNull Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        service.addUser(user);
    }
}
