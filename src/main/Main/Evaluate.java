package main.Main;

import java.util.ArrayList;
import java.util.List;

public class Evaluate {

    public static double evaluateTokens(List<String> tokens) {

        List<String> solve = new ArrayList<>(tokens);

        // First pass: handle * and /
        for (int i = 0; i < solve.size(); i++) {
            String t = solve.get(i);

            if (t.equals("*") || t.equals("/")) {
                double left = Double.parseDouble(solve.get(i - 1));
                double right = Double.parseDouble(solve.get(i + 1));
                double res = t.equals("*") ? left * right : left / right;

                // Replace operation with result
                solve.set(i - 1, String.valueOf(res));
                solve.remove(i);
                solve.remove(i);
                i--;
            }
        }

        // Second pass: handle + and -
        double result = Double.parseDouble(solve.get(0));
        for (int i = 1; i < solve.size(); i += 2) {
            String op = solve.get(i);
            double num = Double.parseDouble(solve.get(i + 1));

            if (op.equals("+")) {
                result += num;
            } else if (op.equals("-")) {
                result -= num;
            }
        }

        return result;
    }
}
