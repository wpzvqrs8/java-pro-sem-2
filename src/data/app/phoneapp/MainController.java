package data.app.phoneapp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.scene.layout.VBox;

import java.time.LocalTime;

public class MainController {

    // PANES
    @FXML private VBox recentPane;
    @FXML private VBox keypadPane;
    @FXML private VBox contactsPane;

    // RECENT
    @FXML private ListView<String> recentList;

    // KEYPAD
    @FXML private TextField numberField;

    // CONTACTS
    @FXML private ListView<String> contactList;

    // ========== INIT ==========
    @FXML
    public void initialize() {
        showRecent();

        // dummy recent data
        recentList.getItems().add("Amit - 99999 - " + LocalTime.now());
        recentList.getItems().add("Ravi - 88888 - " + LocalTime.now());

        // 🔥 DATABASE HOOK POINT (IMPORTANT)
        loadContactsFromDatabase();
    }

    // ========== NAVIGATION ==========
    @FXML
    public void showRecent() {
        recentPane.setVisible(true);
        keypadPane.setVisible(false);
        contactsPane.setVisible(false);
    }

    @FXML
    public void showKeypad() {
        recentPane.setVisible(false);
        keypadPane.setVisible(true);
        contactsPane.setVisible(false);
    }

    @FXML
    public void showContacts() {
        recentPane.setVisible(false);
        keypadPane.setVisible(false);
        contactsPane.setVisible(true);
    }

    // ========== KEYPAD ==========
    @FXML
    public void handleNumber(javafx.event.ActionEvent e) {
        Button b = (Button) e.getSource();
        numberField.appendText(b.getText());
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
        String number = numberField.getText();
        if (!number.isEmpty()) {
            recentList.getItems().add("CALL → " + number + " - " + LocalTime.now());
            showRecent();
        }
    }

    // ========== DATABASE PLACE ==========
    private void loadContactsFromDatabase() {

        /*
         🔥 HERE YOU CONNECT YOUR DB

         Example (JDBC idea):

         SELECT name, number FROM contacts;

         contactList.getItems().add(name + " - " + number);

        */

        // dummy now:
        contactList.getItems().add("John - 11111");
        contactList.getItems().add("Alex - 22222");
    }
}