package data.app.Messages;

import data.Main;
import data.app.Contacts.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
public class Messages extends Application {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    @FXML
    private ListView<Message> message_list  = new ListView<>();
    @FXML
    TextField name,country_code,phoneNumber,email, category, company,notes, address;
    @FXML
    CheckBox favorite;
    @FXML
    Button save_new_button;

    @FXML
    AnchorPane main_contact_frame;
    @FXML
    private Label avatar_label;
    @FXML
    private Label user_name;
    @FXML
    private Label last_msg;
    @FXML
    private Label others_label;
    static Message selected_from_list;
    @FXML
    public void initialize()  {

        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(url, user, password);
            message_from_db = get_contents_from_database();
            message_list.setItems(message_from_db);
            System.out.println(message_from_db);
            message_list.setCellFactory(list -> new MessageCell());
//            System.out.println("Loaded contacts: " + contacts_from_db.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        message_list.setOnMouseClicked(event -> {
            Message selected = message_list.getSelectionModel().getSelectedItem();

            if (selected != null) {
                try {
                    System.out.println(selected.name);
                    openChat(selected);
                }  catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
//        try {
            if(selected_from_list!= null){

            }
//        } catch (Exception e) {}
    }

    public static void openChat(Message selected) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                Messages.class.getResource("open_chat.fxml")
        );
        Parent root = loader.load();
        open_chat controller = loader.getController();
        System.out.println("se"+selected);
        controller.setMessage(selected);
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);


    }

    static void main() throws Exception {
        launch();
    }


    private ObservableList<Message> message_from_db ;

    Parent root = null;
    static Stage stage ;
    @Override
    public void start(Stage stage1) {
        Scene scene;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("messages.fxml")));

        } catch (IOException e) {
        }
        scene = new Scene(root);
        stage = new Stage();
//        Main.prev  = (AnchorPane)data.Main.home_screen_root;
        stage.setScene(scene);
        stage.show();

    }

    static ObservableList<Message> get_contents_from_database() throws Exception {

        ObservableList<Message> list =
                FXCollections.observableArrayList();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM message where name<> 'Bhavy Patel' ORDER BY updated_at asc "
        );

//        contacts_from_db = FXCollections.observableArrayList();


        while (rs.next()) {

            Message messageee = new Message(rs.getString("name"), rs.getString("last_message"), rs.getTimestamp("updated_at").toLocalDateTime());

            list.add(messageee);
        }
//        contacts_list = new ListView<>( contacts_from_db);

//        System.out.println(contacts_from_db.getFirst().name);
        rs.close();
        st.close();
        return list;

    }


}

class Message{
        String name, last_msg ;
        LocalDateTime updated_At;

    public Message(String name, String last_msg, LocalDateTime updated_At) {
        this.name = name;
        this.last_msg = last_msg;
        this.updated_At = updated_At;
    }

    @Override
    public String
    toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", last_msg='" + last_msg + '\'' +
                ", updated_At=" + updated_At +
                '}';
    }
}
