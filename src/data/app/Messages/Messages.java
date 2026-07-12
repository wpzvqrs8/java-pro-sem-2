package data.app.Messages;

import data.app.Contacts.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.time.LocalTime.now;

public class Messages implements Initializable {
    String ReceiverName, phoneNumber, message, senderName;
    @FXML
    private TextArea messageArea;
    @FXML
    private ChoiceBox<String> contactComboBox;
    @FXML
    private TextArea selectedContactLabel;
    @FXML
    private TextArea display;
    @FXML
    private TextArea messageTextArea;

    private int id =1;

    private String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    private String user = "postgres";
    private String password = "postgres";
    private Connection conn  = DriverManager.getConnection(url,user,password);;

    public Messages(String receiverName, String phoneNumber, String senderName) throws SQLException {
        ReceiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.senderName = senderName;
    }
    public void send(ActionEvent event)  {
        try{

            FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(Contacts.class.getResource("add_new_contact.fxml")));
            Parent root = loader.load();
            root.setLayoutY(25);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene= new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
    public void newContact(ActionEvent event){
        try{
            Contacts contact = new Contacts();
            contact.add_contact(event);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    void sendMessage(ActionEvent event) throws SQLException {
        message = messageArea.getText();
        String receiver_name = contactComboBox.getValue();
        display.setText("Send Message to: " + receiver_name);
        String query = "INSERT INTO messages(id,receiver_name,extra,message) VALUES(?,?,now(),?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,id);
        statement.setString(2,receiver_name);
        statement.setString(3,message);
        id++;
        statement.executeUpdate();
        messageArea.setText("Message sent");
        selectedContactLabel.setText(receiver_name);
    }
    void cancel(ActionEvent event){
        messageArea.setText("");
    }
    void displayMessage(ActionEvent event) throws SQLException {
        String sql = "SELECT message FROM messages ORDER BY messages_id";

        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet set = statement.executeQuery();

        StringBuilder messages = new StringBuilder();

        while (set.next()) {
            messages.append(set.getString("message"))
                    .append("\n");
        }

        messageTextArea.setText(messages.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> data = new ArrayList<>();
        String query = "SELECT name FROM contacts";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                data.add(set.getString("name"));
            }
            contactComboBox.getItems().addAll(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
