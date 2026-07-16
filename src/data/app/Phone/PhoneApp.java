package data.app.Phone;
import data.Main;
import data.app.Contacts.Contacts;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalTime;
import java.util.Objects;

import javafx.scene.control.Alert;

public class PhoneApp extends Application {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "";
    static Connection conn;

    static { try {
        Class.forName("org.postgresql.Driver");

        conn = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }}
    Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("phone.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Phone App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // PANES
    @FXML
    private VBox recentPane;


    // RECENT
    @FXML private ListView<String> recentList;

    // KEYPAD
    @FXML private Label numberField;

    // CONTACTS
//    @FXML private ListView<String> contactList;


    @FXML
    public void initialize() {
        if(recentList!=null){

//            // dummy recent data
//            recentList.getItems().add("Amit - 99999 - " + LocalTime.now());
//            recentList.getItems().add("Ravi - 88888 - " + LocalTime.now());


        }
    }


    @FXML
    public void showRecent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("phone.fxml"));
        Parent root  = loader.load();
//        Stage s = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        s.getScene().setRoot(root);
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);
    }

    @FXML
    public void showKeypad(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("keypad.fxml"));
        Parent root  = loader.load();
//        Stage s = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        s.getScene().setRoot(root);
        Main.MAIN_SCENE.getChildren().setAll(root);


    }

    @FXML
    public void showContacts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Contacts.class.getResource("contacts_main_frame.fxml"));
        Parent root = loader.load();
        root.setLayoutY(15);
        Main.MAIN_SCENE.getChildren().setAll(root);;
    }

    // ========== KEYPAD ==========
    @FXML
    public void handleNumber(ActionEvent e) {

        Button b = (Button) e.getSource();

        numberField.setText(numberField.getText() + b.getText());

    }

    @FXML
    public void delete() {

        String text = numberField.getText();

        if (!text.isEmpty()) {

            numberField.setText(text.substring(0, text.length() - 1));

        }

    }
    @FXML
    public void call() {

        String number = numberField.getText().trim();

        // Empty number
        if (number.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a phone number.");
            alert.show();

            return;
        }

        // Check only digits
        if (!number.matches("\\d+")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid");
            alert.setHeaderText(null);
            alert.setContentText("Phone number must contain only digits.");
            alert.show();

            return;
        }

        // Must be 10 digits
        if (number.length() != 10) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Number");
            alert.setHeaderText(null);
            alert.setContentText("Phone number must be exactly 10 digits.");
            alert.show();

            return;
        }

        // Dummy contact names
        String name = "Unknown";

        if(number.equals("9999999999"))
            name = "Amit";

        else if(number.equals("8888888888"))
            name = "Ravi";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Calling");
        alert.setHeaderText(null);
        alert.setContentText("Call has been placed to\n\nName : " + name +
                "\nNumber : " + number);

        alert.show();

        if(recentList != null){
            recentList.getItems().add(name + " - " + number + " - " + LocalTime.now());
        }

        numberField.setText("");

    }
}