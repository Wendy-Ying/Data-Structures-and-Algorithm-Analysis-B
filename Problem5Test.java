import edu.princeton.cs.algs4.Stack;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem5Test {
    public static Matcher split(String input) {
        Pattern pattern = Pattern.compile("\\d+|\\+|-|\\*|/|\\(|\\)");
        return pattern.matcher(input);
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSymbol(String s) {
        return s.matches("[+\\-*/]");
    }

    public static int LevelCompare(String sign1, String sign2) {
        int level1 = 0;
        int level2 = 0;
        int compare = 10;
        if (sign1.equals("(") || sign1.equals(")"))
            level1 = -1;
        if (sign2.equals("(") || sign2.equals(")"))
            level2 = -1;
        if (sign1.equals("*") || sign1.equals("/"))
            level1 = 2;
        if (sign2.equals("*") || sign2.equals("/"))
            level2 = 2;
        if (sign1.equals("+") || sign1.equals("-"))
            level1 = 1;
        if (sign2.equals("+") || sign2.equals("-"))
            level2 = 1;
        if (level1 > level2)
            compare = 1;
        if (level1 < level2)
            compare = -1;
        if (level1 == level2)
            compare = 0;
        return compare;
    }

    public static double calculatePostfix(ArrayList<String> result) {
        ArrayList<String> list = new ArrayList<>();
        double num1;
        double num2;
        String operator;
        double num3 = 0;
        if (result.size() == 1) {
            return Double.parseDouble(result.getFirst());
        }
        for(int i = 0; i < result.size(); i++) {
            list.add(result.get(i));
            if (i>1 && isSymbol(result.get(i))) {
                num1 = Double.parseDouble(result.get(i - 2));
                num2 = Double.parseDouble(result.get(i - 1));
                operator = result.get(i);
                if (operator.equals("+"))
                    num3 = num1 + num2;
                if (operator.equals("-"))
                    num3 = num1 - num2;
                if (operator.equals("*"))
                    num3 = num1 * num2;
                if (operator.equals("/"))
                    num3 = num1 / num2;
                list.remove(i);
                list.remove(i - 1);
                list.set(i - 2, Double.toString(num3));
                list.addAll(result.subList(i + 1, result.size()));
                break;
            }
        }
        return calculatePostfix(list);//recursive
    }

    public static double postfix(String text) {
        Stack<String> stack = new Stack<>();
        Stack<String> flag = new Stack<>();
        ArrayList<String> result = new ArrayList<>();
        String temp;
        Matcher matcher = split(text);
        //analyze the signs
        while (matcher.find()) {
            temp = matcher.group();
            if (isNumeric(temp)) {
                stack.push(temp);
            } else if (temp.equals("(")) {
                flag.push(temp);
            } else if (temp.equals(")")) {
                while (!flag.peek().equals("(")) {
                    stack.push(flag.pop());
                }
                flag.pop();
            } else {
                while (!flag.isEmpty() && LevelCompare(flag.peek(), temp) >= 0)
                    stack.push(flag.pop());
                flag.push(temp);
            }
        }
        while (!flag.isEmpty())
            stack.push(flag.pop());
        //copy a reverse version
        while (!stack.isEmpty())
            flag.push(stack.pop());
        while (!flag.isEmpty()) {
            temp = flag.pop();
            result.add(temp);
            System.out.print(temp + " ");
        }
        System.out.println();
        //compute
        double output = calculatePostfix(result);
        System.out.printf("result of postfix: %.2f%n", output);
        return output;
    }

    public static void calculateInfix(Stack<Double> value, char sign) { // value sign value -> value
        double value2 = value.pop();
        double value1 = value.pop();
        switch (sign) {
            case '+':
                value.push(value1 + value2);
                break;
            case '-':
                value.push(value1 - value2);
                break;
            case '*':
                value.push(value1 * value2);
                break;
            case '/':
                value.push(value1 / value2);
                break;
        }
    }

    public static double infix(String text) {
        Stack<Character> operator = new Stack<>();
        Stack<Double> numbers = new Stack<>();
        int i = 0;
        while (i < text.length()){
            char c = text.charAt(i);
            if (Character.isDigit(c)) { // number
                StringBuilder sign = new StringBuilder();
                while (i < text.length() && (Character.isDigit(text.charAt(i)))) {
                    sign.append(text.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Double.parseDouble(sign.toString()));
            }
            if (c == '(') {
                operator.push(c);
            }
            if (c == ')') {
                while (!operator.isEmpty() && operator.peek() != '(') { //calculate()
                    calculateInfix(numbers, operator.pop());
                }
                operator.pop();
            }
            if (c == '+' || c == '-' || c == '*' || c == '/') { //calculate
                while (!operator.isEmpty() && LevelCompare(operator.peek().toString(),Character.toString(c)) >= 0) {
                    calculateInfix(numbers, operator.pop());
                }
                operator.push(c);
            }
            i++;
        }
        while (!operator.isEmpty()) {
            calculateInfix(numbers, operator.pop());
        }
        double output = numbers.pop();
        System.out.printf("result of infix: %.2f%n", output);
        return output;
    }

    public static boolean check(String text) {
        System.out.println("infix: "+text);
        System.out.print("postfix: ");
        double result1 = postfix(text);
        double result2 = infix(text);
        return result1 == result2;
    }

    public static String genData(int length) {
        Random rand = new Random();
        StringBuilder expression = new StringBuilder();
        char[] operators = {'+', '-', '*', '/'};
        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                expression.append(rand.nextInt(10001));
            } else {
                expression.append(operators[rand.nextInt(operators.length)]);
            }
        }
        return expression.toString();
    }

    public static void main(String[] args) {
        int flag = 0;
        for (int i = 1; i <= 100; i=i+2) {
            if (!check(genData(i))) {
                System.out.println(i + " false");
                flag = 1;
            }
            else System.out.println(i + " true");
        }
        if (flag == 1)
            System.out.println("completed with some problem, please check");
        else
            System.out.println("completed with no problem");
    }
}
