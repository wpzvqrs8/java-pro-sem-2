//package referances.calculator_expression;
//
//import java.util.*;
//
//class Parser {
//
//    static int precedence(String op) {
//        return switch (op) {
//            case "+", "-" -> 1;
//            case "*", "/" -> 2;
//            default -> -1;
//        };
//    }
//
//    static List<String> toPostfix(String exp) {
//        List<String> output = new ArrayList<>();
//        Stack<String> stack = new Stack<>();
//
//        String[] tokens = exp.split(" ");
//
//        for (String token : tokens) {
//
//            if (token.matches("\\d+(\\.\\d+)?")) {
//                output.add(token);
//            }
//
//            else if ("+-*/".contains(token)) {
//                while (!stack.isEmpty() &&
//                        precedence(stack.peek()) >= precedence(token)) {
//                    output.add(stack.pop());
//                }
//                stack.push(token);
//            }
//        }
//
//        while (!stack.isEmpty()) {
//            output.add(stack.pop());
//        }
//
//        return output;
//    }
//
//    static Node buildTree(List<String> postfix) {
//        Stack<Node> stack = new Stack<>();
//
//        for (String token : postfix) {
//
//            if (token.matches("\\d+(\\.\\d+)?")) {
//                stack.push(new Node(token));
//            } else {
//                Node right = stack.pop();
//                Node left = stack.pop();
//
//                Node op = new Node(token);
//                op.left = left;
//                op.right = right;
//
//                stack.push(op);
//            }
//        }
//
//        return stack.pop();
//    }
//}