package data.app.Calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Calculator extends Application {
    static  Stage stage;
    @FXML
    private ListView<String> history_list;
    double solution = 0.0;
    boolean is_error;
    @Override
    public void start(Stage stage1) throws Exception {
        stage = stage1;
        Parent root  = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Calculator.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }
    static void main() {launch();}

    @FXML
    public void initialize() throws IOException {
//        history_list = new ListView<>();
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    void add_to_History_list(String calc){

    }
}
