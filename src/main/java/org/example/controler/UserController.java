package org.example.controler;

import io.javalin.http.Context;
import io.javalin.openapi.*;
import io.javalin.validation.Validator;
import org.example.db.JdbiConfiguration;
import org.example.domain.User;
import org.example.service.UserService;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class UserController {

    private final UserService service;
    public Jdbi jdbi;

    public UserController() {
        jdbi = new JdbiConfiguration().createJdbi();
        SqlLogger sqlLogger = new SqlLogger() {
            @Override
            public void logBeforeExecution(StatementContext context) {
                SqlLogger.super.logBeforeExecution(context);
                System.out.println(context.getRawSql());
            }
        };
        jdbi.setSqlLogger(sqlLogger);
        service = new UserService(jdbi);

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

        System.out.println("Get All Users Receive Request at :" + Thread.currentThread() + " time: " + System.currentTimeMillis());
        var listOfUser = service.getAllUser();

        ctx.result(listOfUser.toString());
    }

    @OpenApi(
            summary = "User exist",
            operationId = "exist user",
            path = "/api/v1/exist",
            queryParams = {@OpenApiParam(name = "userId", type = Integer.class, description = "The user ID")},
            methods = HttpMethod.GET,
            tags = {"User"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User.class)})
            }
    )
    public void existUser(@NotNull Context ctx) {

        System.out.println("Exist User Receive Request at :" + Thread.currentThread() + " time: " + System.currentTimeMillis());
        int userId = ctx.queryParamAsClass("userId", Integer.class).get();

        Boolean existUser = service.existUser(userId);

        ctx.result(existUser.toString());
    }


    @OpenApi(
            summary = "Add a user",
            operationId = "add user",
            path = "/api/v1/addUser",
            methods = HttpMethod.POST,
            tags = {"User"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = User.class)}),
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User[].class)})
            }
    )
    public void addUser(@NotNull Context ctx) {
        System.out.println("Add User Receive Request at :" + Thread.currentThread() + " time: " + System.currentTimeMillis());

        User user = ctx.bodyValidator(User.class)
                       .check(usr -> Objects.nonNull(usr.userid()), "UserId couldn't be null")
                       .get();

        service.addUser(user);
    }
}
