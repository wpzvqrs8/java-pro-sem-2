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

public class PhoneApp extends Application {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "";
    static Connection conn;

    static { try {
        Class.forName("org.postgresql.Driver");

        conn = DriverManager.getConnection(url, user, password);
        loadContactsFromDatabase();
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

    // ========== INIT ==========
    @FXML
    public void initialize() {
        if(recentList!=null){

//            // dummy recent data
//            recentList.getItems().add("Amit - 99999 - " + LocalTime.now());
//            recentList.getItems().add("Ravi - 88888 - " + LocalTime.now());


        }
        loadContactsFromDatabase();
    }

    // ========== NAVIGATION ==========
    @FXML
    public void showRecent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("phone.fxml"));
        Parent root  = loader.load();
//        Stage s = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        s.getScene().setRoot(root);
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
    public void showContacts() throws IOException {

            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(Contacts.class.getResource("contacts_main_frame.fxml")));
            root.setLayoutY(10);

        Main.MAIN_SCENE.getChildren().setAll(root);

    }

    // ========== KEYPAD ==========
    @FXML
    public void handleNumber(javafx.event.ActionEvent e) {
//        Button b = (Button) e.getSource();
//        numberField.appendText(b.getText());
    }

    @FXML
    public void delete() {
//        String text = numberField.getText();
//        if (!text.isEmpty()) {
//            numberField.setText(text.substring(0, text.length() - 1));
//        }
    }

    @FXML
    public void call() {
//        String number = numberField.getText();
//        if (!number.isEmpty()) {
//            recentList.getItems().add("CALL → " + number + " - " + LocalTime.now());
//            showRecent();
//        }
    }

    // ========== DATABASE PLACE ==========
    private static void loadContactsFromDatabase() {

        /*
         🔥 HERE YOU CONNECT YOUR DB

         Example (JDBC idea):

         SELECT name, number FROM contacts;

         contactList.getItems().add(name + " - " + number);

        */

//        // dummy now:
//        contactList.getItems().add("John - 11111");
//        contactList.getItems().add("Alex - 22222");
    }

}