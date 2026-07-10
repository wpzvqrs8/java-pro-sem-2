package data.app.Calculator;

import data.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.*;

public class Calculator extends Application {
    static  Stage stage;
    @FXML
    private ListView<String> history_list;
    @FXML
    TextField expression_text;
    double solution = 0.0;
    boolean is_error;
    @Override
    public void start(Stage stage1) throws Exception {
        stage = stage1;
        Parent root  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Calculator.fxml")));
        Main.prev_screen_stack.push(root);
        stage.setScene(new Scene(root));
        stage.show();
    }
    static void main() {launch();}

    @FXML
    public void initialize() throws IOException {
//        history_list = new ListView<>();
//        Parent root  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Calculator.fxml")));
//        Main.prev_screen_stack.push(root);
        System.out.println(history_list);
        System.out.println("init");
        if (history_list != null){
            history_list.setEditable(false);
            history_list.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");
            String HISTORY_FILE = "src/data/data/calculator_history.txt";
            history_list.getItems().setAll(Files.readAllLines(new File(HISTORY_FILE).toPath()));
//            System.out.println(Files.readAllLines(Path.of(HISTORY_FILE)));

        }
    }
    @FXML
    void show_history(ActionEvent event) throws IOException {

        Parent root  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("History.fxml")));
        root.setLayoutY(25);
        Main.prev_screen_stack.push(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();
        data.Main.MAIN_SCENE.getChildren().setAll(root);

    }

    void add_to_History_list(String calc){

    }

    private String expression = "";
    @FXML
    public void one(ActionEvent event){
        expression += "1 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void two(ActionEvent event){
        expression += "2 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void three(ActionEvent event){
        expression += "3 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void four(ActionEvent event){
        expression += "4 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void five(ActionEvent event){
        expression += "5 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void six(ActionEvent event){
        expression += "6 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void seven(ActionEvent event){
        expression += "7 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void eight(ActionEvent event){
        expression += "8 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void nine(ActionEvent event){
        expression += "9 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void zero(ActionEvent event){
        expression += "0 ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void addition(ActionEvent event){
        expression += "+ ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void subtraction(ActionEvent event){
        expression += "- ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void multiplication(ActionEvent event){
        expression += "* ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void division(ActionEvent event){
        expression += "/ ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void dot(ActionEvent event){
        expression += ". ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void equal(ActionEvent event){
        ExpressionTreeEngine solver = new ExpressionTreeEngine();
        solver.evaluate(expression);

    }
    public void remove(ActionEvent event){
        if (expression_text != null && !expression.isEmpty()) {
            expression_text.setText(expression.substring(0, expression.length() - 2));
        }
    }
    public void removeAll(ActionEvent event){
        expression_text.clear();
        expression = "";
    }
}
class Nodes {
    String value;
    Nodes left, right;

    Nodes(String value) {
        this.value = value;
    }

    boolean isOperator() {
        return value.equals("+") ||
                value.equals("-") ||
                value.equals("*") ||
                value.equals("/");
    }
}
class ExpressionTreeEngine {
    public double evaluate(String expression) {

            List<String> postfix = Expression_Parser.toPostfix(expression);
            Nodes root = Expression_Parser.buildTree(postfix);

            return evaluateTree(root);
        }

        private double evaluateTree(Nodes root) {

            if (!root.isOperator()) {
                return Double.parseDouble(root.value);
            }

            double left = evaluateTree(root.left);
            double right = evaluateTree(root.right);

            return switch (root.value) {
                case "+" -> left + right;
                case "-" -> left - right;
                case "*" -> left * right;
                case "/" -> left / right;
                default -> 0;
            };
        }
}
class Expression_Parser {

    static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> -1;
        };
    }

    static List<String> toPostfix(String exp) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        String[] tokens = exp.split(" ");

        for (String token : tokens) {

            if (token.matches("\\d+(\\.\\d+)?")) {
                output.add(token);
            }

            else if ("+-*/".contains(token)) {
                while (!stack.isEmpty() &&
                        precedence(stack.peek()) >= precedence(token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    static Nodes buildTree(List<String> postfix) {
        Stack<Nodes> stack = new Stack<>();

        for (String token : postfix) {

            if (token.matches("\\d+(\\.\\d+)?")) {
                stack.push(new Nodes(token));
            } else {
                Nodes right = stack.pop();
                Nodes left = stack.pop();

                Nodes op = new Nodes(token);
                op.left = left;
                op.right = right;

                stack.push(op);
            }
        }

        return stack.pop();
    }
}