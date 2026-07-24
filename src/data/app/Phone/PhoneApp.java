package data.app.Phone;
import data.Main;
import data.app.Contacts.Contacts;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.util.Duration;

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
    //loadRecentCalls
    Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(PhoneApp.class.getResource("phone.fxml"));

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

    @FXML
    private Button recent_btn,keypad_btn;
    @FXML private ListView<String> recentList;

    // KEYPAD
    @FXML private TextField numberField;

    // CONTACTS
//    @FXML private ListView<String> contactList;

    // ========== INIT ==========
    @FXML
    public void initialize() {
        if (recentList != null) {
            recentList.setStyle("-fx-font-family: \"Inter\";-fx-control-inner-background: #b3bab5;-fx-font-size: 15px;-fx-font-weight: 600;" );
            recent_btn.setDisable(true);
        }

        if(keypad_btn!=null){
            keypad_btn.setDisable(true);
        }
        loadRecentCalls();

    }
    @FXML
    private void loadRecentCalls() {

        if (recentList == null)
            return;
        recentList.getItems().clear();
        try {
//
//            INSERT INTO call_history (
//                    contact_id,
//                    name,
//                    phone_number,
//                    call_status,
//                    duration_seconds,
//                    started_at,
//                    ended_at
//            )
            String sql = "SELECT * FROM call_history ORDER BY started_at DESC";
            PreparedStatement ps =
                    conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("started_at");
                recentList.getItems().add(rs.getString("name") +
                                "  ( " + String.format("%.2f ", rs.getDouble("duration_seconds") / 60) + ")\n" +rs.getString("phone_number") +
                                "              " +ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MMMM")) +
                                "\n                                      " + ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
                );
            }

        }
        catch (Exception e) {e.printStackTrace();}

    }

    @FXML
    private void showKeypad(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("keypad.fxml"));
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);

    }

    @FXML
    public void showContacts(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/data/app/Contacts/contacts_main_frame.fxml"));

        Parent root = loader.load();
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);    }

    @FXML
    public void handleNumber(ActionEvent e) {
        if (numberField.getText().equals("Enter Phone Number")) {
            numberField.setText("");
        }
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
        // Empty
        if (number.isEmpty()) {
            numberField.setText("Enter Number");
            PauseTransition pt = new PauseTransition(Duration.seconds(2));
            pt.setOnFinished(e->numberField.setText(number));
            pt.play();
            return;
        }
        // Only digits
        if (!number.matches("\\d+")) {

            numberField.setText("Only digits allowed");
            PauseTransition pt = new PauseTransition(Duration.seconds(2));
            pt.setOnFinished(e->numberField.setText(number));
            pt.play();
            return;
        }

        // Exactly 10 digits
        if (!number.startsWith("+") && number.length() != 10) {

            numberField.setText("Phone number must be 10 digits");
            PauseTransition pt = new PauseTransition(Duration.seconds(2));
            pt.setOnFinished(e->numberField.setText(number));
            pt.play();
            return;
        }

        // First digit must be 6,7,8,9
        if (!number.startsWith("+") && !(number.startsWith("6") ||
                number.startsWith("7") ||
                number.startsWith("8") ||
                number.startsWith("9"))) {
            numberField.setText("Invalid Mobile Number");
            PauseTransition pt = new PauseTransition(Duration.seconds(2));
            pt.setOnFinished(e->numberField.setText(number));
            pt.play();
            return;
        }

        String name = "Unknown";

        try {

            System.out.println(number);
            String search =
                    "SELECT name FROM contacts WHERE phone_number=? limit 1";

            PreparedStatement ps = conn.prepareStatement(search);
            if(number.startsWith("+")){
                ps.setString(1,number);
            }
            else ps.setString(1, "+91 "+number);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                System.out.println(name);
            }
//
            // Insert into Recent Table

            String insert =
                    "INSERT INTO call_history(name,phone_number,call_status ,started_at,ended_at) VALUES(?,?,?,?,?)";
            PreparedStatement insertPS = conn.prepareStatement(insert);
            insertPS.setString(1, name);
            insertPS.setString(2, number);
            insertPS.setString(3, "Answered");
            insertPS.setTimestamp(4, Timestamp.from(Instant.now()));
            insertPS.setTimestamp(5,Timestamp.from(Instant.now().plusSeconds(60)));
            System.out.println(Timestamp.from(Instant.now()));
            System.out.println(Timestamp.from(Instant.now().plusSeconds(60)));
            insertPS.executeUpdate();
            if (recentList != null) {
                recentList.getItems().add(0,
                        name + "\n" + number);
            }

            numberField.clear();

            System.out.println("Calling " + name);
            showRecent();
        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    void  showRecent() throws IOException {
        Parent root =  FXMLLoader.load(PhoneApp.class.getResource("phone.fxml"));
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);
    }
}