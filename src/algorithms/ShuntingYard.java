package algorithms;

import collection.list.ArrayList;
import collection.queue.LinkedQueue;
import collection.stack.LinkedStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuntingYard {

    public final LinkedQueue<String> toRPN(final String input) {
        LinkedStack<String> stack = new LinkedStack<>();
        LinkedQueue<String> queue = new LinkedQueue<>();

        ArrayList<String> expression = splitExpression(input);

        for (String e : expression) {
            if (isOperator(e)) {
                while (!stack.isEmpty() && isHigerPrec(e, stack.top())) {
                    queue.enqueue(stack.pop());
                }
                stack.push(e);

            } else if (e.equals("(")) {
                stack.push(e);

            } else if (e.equals(")")) {
                while (!stack.top().equals("(")) {
                    queue.enqueue(stack.pop());
                }
                stack.pop();

            } else {
                queue.enqueue(e);
            }
        }

        while (!stack.isEmpty()) {
            queue.enqueue(stack.pop());
        }

        return queue;
    }

    public final Double evaluate(final String input) {
        LinkedQueue<String> expression = toRPN(input);
        LinkedStack<Double> stack = new LinkedStack<>();

        while (!expression.isEmpty()) {
            String element = expression.dequeue();

            if (isNumber(element)) {
                stack.push(Double.parseDouble(element));
            } else {
                double second = stack.pop();
                double first = stack.pop();
                stack.push(calculate(first, element, second));
            }
        }

        return stack.pop();
    }

    private Double calculate (double first, String operator, double second) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                return first / second;
        }
        return null;
    }

    private ArrayList<String> splitExpression(String expression) {
        Pattern p = Pattern.compile("\\d+\\.\\d+|\\d+|[-+*/()]");
        Matcher m = p.matcher(expression);

        ArrayList<String> result = new ArrayList<>();
        while (m.find()) {
            result.add(m.group(1));
        }

        return result;
    }

    private int precedence(String op) {
        switch (op) {
            case "+":
            case "-":
                return 0;
            case "*":
            case "/":
                return 1;
            default:
                throw new IllegalArgumentException("Operator unknown: " + op);
        }
    }

    private boolean isHigerPrec(String op, String sub) {
        return (isOperator(sub) && precedence(sub) >= precedence(op));
    }

    private boolean isNumber(String num) {
        return num.matches("(\\d\\.\\d+|\\d+)");
    }

    private boolean isOperator(String op) {
        return op.matches("[-+*/]");
    }

}
