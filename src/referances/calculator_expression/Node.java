package referances.calculator_expression;

class Node {
    String value;
    Node left, right;

    Node(String value) {
        this.value = value;
    }

    boolean isOperator() {
        return value.equals("+") ||
                value.equals("-") ||
                value.equals("*") ||
                value.equals("/");
    }
}
//