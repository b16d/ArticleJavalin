package org.example.db.doa;

import org.example.domain.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.util.List;

public class UserDao {

    private final Jdbi jdbi;

    public UserDao(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(ConstructorMapper.factory(User.class));
    }

    public List<User> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        select firstname, famillyname, userid
                        from user3 """)
                .mapTo(User.class)
                .list());
    }

    public Integer existUser(int userid) {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        select count (*)
                        from user3
                        where userId = :userid""")
                .bind("userid", userid)
                .mapTo(Integer.class)
                .one());
    }

/*
    try (Handle handle = jdbi.open()) {
    for (int i = 0; i < 100_000; i++) {
        try (Update update = handle.createUpdate("INSERT INTO users (id, name) VALUES (:id, :name)")) {
            update.bind("id", i)
                  .bind("name", "user_" + i)
                  .execute();
        }
VALUES ('Alban', 'Clevy', 1);
    }
}
     */

    public void addUser(User user) {
        jdbi.withHandle(handle -> handle.createUpdate("""
                                INSERT INTO user3 (firstname, famillyname, userid)
                                VALUES (:firstname, :famillyname, :userid)""")
                .bind("firstname", user.firstname())
                .bind("famillyname", user.famillyname())
                .bind("userid", user.userid())
                .execute());
    }
}
