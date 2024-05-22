package org.example.controler;

import io.javalin.config.JavalinConfig;
import org.example.db.JdbiConfiguration;
import org.example.db.doa.UserDao;
import org.jdbi.v3.core.Jdbi;

public class ControllerFactory {
    public void createAndBindController(JavalinConfig config) {
        Jdbi jdbi = new JdbiConfiguration().createJdbi();
        UserDao userDao = new UserDao(jdbi);
    }
}
