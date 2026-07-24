package data.app.Calculator;

import data.Main;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import referances.calculator_expression.ExpressionTreeEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
    String HISTORY_FILE = "src/data/data/calculator_history.txt";
    RandomAccessFile raf;

    {
        try {
            raf = new RandomAccessFile(HISTORY_FILE,"rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() throws IOException {
//        history_list = new ListView<>();
//        Parent root  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Calculator.fxml")));
//        Main.prev_screen_stack.push(root);
        System.out.println(history_list);
//        System.out.println("init");
        if (history_list != null){
            history_list.setEditable(false);
            history_list.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial'; -fx-background-color:black; ");
            history_list.setCellFactory(lv -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial'; -fx-background-color:black;   -fx-text-fill:white;  ");
                    } else {
                        setText(item);
                        setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial'; -fx-background-color:black;   -fx-text-fill:white;  ");
                    }
                }
            });
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
        Main.recent_apps_stack.pop();
        Main.recent_apps_stack.push(root);

    }

    void add_to_History_list(String calc) throws Exception {
        raf.seek(raf.length());
        raf.write(("\n"+calc).getBytes());
    }

    private String expression = "";
    @FXML
    public void one(ActionEvent event){
        expression += "1";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void two(ActionEvent event){
        expression += "2";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void three(ActionEvent event){
        expression += "3";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void four(ActionEvent event){
        expression += "4";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void five(ActionEvent event){
        expression += "5";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void six(ActionEvent event){
        expression += "6";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void seven(ActionEvent event){
        expression += "7";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void eight(ActionEvent event){
        expression += "8";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void nine(ActionEvent event){
        expression += "9";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void zero(ActionEvent event){
        expression += "0";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void addition(ActionEvent event){
        expression += " + ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void subtraction(ActionEvent event){
        expression += " - ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void multiplication(ActionEvent event){
        expression += " * ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void division(ActionEvent event){
        expression += " / ";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void dot(ActionEvent event){
        expression += ".";
        expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public void equal(ActionEvent event){
        try {
        String[] data = expression.split(" ");

        ArrayDeque<Double> numbers = new ArrayDeque<>();
        ArrayDeque<String> operators = new ArrayDeque<>();
        for(int i =0; i< data.length;i++){
            if(data[i].matches(".*[+\\-*/].*")){
                operators.add(data[i]);
            }else{
                numbers.add(Double.parseDouble(data[i]));
            }
        }

        ExpressionTreeEngine e = new ExpressionTreeEngine();

            add_to_History_list(expression_text.getText()+" = "+e.evaluate(expression_text.getText()));
            expression_text.setText(""+e.evaluate(expression_text.getText()));
        } catch (Exception ex) {
            expression_text.setText("Error");
            PauseTransition pt = new PauseTransition(Duration.seconds(1.5));
            pt.setOnFinished(e->expression_text.setText(""));
            pt.play();
        }
        expression= "";

    }
    @FXML
    public  void removeLast(ActionEvent event){
       if(expression_text.getText().charAt(expression_text.getLength()-1)==' '){
           expression = expression.substring(0,expression.length()-2);
       }
       else
           expression = expression.substring(0,expression.length()-1);

           expression_text.setText(expression);
        expression_text.end();
    }
    @FXML
    public  void clearAll(ActionEvent event){
        expression = "";
        expression_text.setText(expression);
        expression_text.end();
    }

}
