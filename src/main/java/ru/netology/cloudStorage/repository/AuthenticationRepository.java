package ru.netology.cloudStorage.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AuthenticationRepository {

    private final Map<String, String> tokens = new ConcurrentHashMap<>();


    public void putTokenAndLogin(String token, String login) {
        tokens.put(token, login);
    }

    public String getLoginByToken(String token) {
        return tokens.get(token);
    }

    public void removeTokenAndUsernameByToken(String token) {
        tokens.remove(token);
    }

    public String getTokenByLogin(String login) {
        Set<Map.Entry<String, String>> entrySet = tokens.entrySet();

        String token = "";
        for (Map.Entry<String, String> pair : entrySet) {
            if (login.equals(pair.getValue())) {
                token = pair.getKey();
            }
        }
        if (token.equals("")) {
            return null;
        }
        return token;
    }
}