//package referances.calculator_expression;
//import java.util.*;
//import javax.script.*;
//
//public class ExpressionTreeEngine {
//    public double evaluate(String expression) {
//
//            List<String> postfix = Parser.toPostfix(expression);
//            Node root = Parser.buildTree(postfix);
//
//            return evaluateTree(root);
//        }
//
//        private double evaluateTree(Node root) {
//
//            if (!root.isOperator()) {
//                return Double.parseDouble(root.value);
//            }
//
//            double left = evaluateTree(root.left);
//            double right = evaluateTree(root.right);
//
//            return switch (root.value) {
//                case "+" -> left + right;
//                case "-" -> left - right;
//                case "*" -> left * right;
//                case "/" -> left / right;
//                default -> 0;
//            };
//        }
//    }