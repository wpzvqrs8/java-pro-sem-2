package referances;

import referances.calculator_expression.ExpressionTreeEngine;

class Test {
    public static void main(String[] args) {
        ExpressionTreeEngine e = new ExpressionTreeEngine();
        System.out.println(e.evaluate("10 + 100"));;

    }
}