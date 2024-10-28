import edu.princeton.cs.algs4.Stack;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem5 {
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
        return compare;//>1 <-1 =0
    }

    public static double calculate(ArrayList<String> result) {
        ArrayList<String> list = new ArrayList<>();
        double num1 = 0;
        double num2 = 0;
        String operator = null;
        double num3 = 0;
        if (result.size() == 1) {
            return Double.parseDouble(result.getFirst());
        }
        for(int i = 0; i < result.size(); i++) {
            list.add(result.get(i));
            if (i>1 && isSymbol(result.get(i))) {
                num1 = Double.parseDouble(result.get(i - 2));
                num2 = Double.parseDouble(result.get(i - 1));
                operator = result.get(i);//calculate
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
        return calculate(list);
    }

    public static void main(String[] args) {
        //initialize
        Scanner sc = new Scanner(System.in);
        Stack<String> stack = new Stack<>();
        Stack<String> flag = new Stack<>();
        ArrayList<String> result = new ArrayList<String>();
        String text;
        String temp = null;

        while (sc.hasNext()) {
            text = sc.next();
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
                if (!flag.isEmpty())
                    System.out.print(temp + " ");
                else
                    System.out.print(temp + ',');
            }

            //compute
            double output = calculate(result);
            System.out.printf("%.2f%n",output);
        }
    }
}
