package main.Main;

import java.util.ArrayList;
import java.util.List;

public class Operation {

    private List<String> tokens = new ArrayList<>();

    public void addToken(String token) {
        tokens.add(token);
    }

    public List<String> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return String.join(" ", tokens);
    }
}
