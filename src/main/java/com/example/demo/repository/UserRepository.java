package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<Integer, User> usersDatabase;
    private final int MAX_ATTEMPTS = 3;

    public enum LoginStatus {
        ACCESS,
        UNAUTHORIZED,
        FORBIDDEN
    }

    public UserRepository() {
        usersDatabase = new HashMap<>();

        usersDatabase.put(1, new User("cracker", "cracker1234", true, 0));
        usersDatabase.put(2, new User("marry", "marietta!#09", true, 0));
        usersDatabase.put(3, new User("silver", "$silver$", true, 0));
    }

    public LoginStatus checkLogin(final String login, final String password) {
        for (Map.Entry<Integer, User> entry: usersDatabase.entrySet()) {
            User user = entry.getValue();
            if (user.getLogin().equals(login)) {
                if (user.getIncorrectLoginCounter() >= MAX_ATTEMPTS) {
                    return LoginStatus.FORBIDDEN;
                }

                if (!user.getPassword().equals(password)) {
                    user.incrementIncorrectLoginCounter();
                    usersDatabase.put(entry.getKey(), user);
                    return LoginStatus.UNAUTHORIZED;
                }
                return LoginStatus.ACCESS;
            }
        }

        return LoginStatus.UNAUTHORIZED;
    }
}
