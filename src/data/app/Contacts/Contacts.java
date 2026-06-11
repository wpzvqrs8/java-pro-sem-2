package data.app.Contacts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Contacts extends Application {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    @FXML
    private ListView<Contact> contacts_list  = new ListView<>();


    @FXML
    public void initialize()  {
        try {
            Class.forName("org.postgresql.Driver");

        conn = DriverManager.getConnection(url, user, password);
            contacts_from_db = get_contents_from_database();
            contacts_list.setItems(contacts_from_db);
            System.out.println("Loaded contacts: " + contacts_from_db.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void main() throws Exception {
        launch();
    }


    private ObservableList<Contact> contacts_from_db ;

    @Override
    public void start(Stage stage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("contacts_main_frame.fxml")));
        } catch (IOException e) {
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    static ObservableList<Contact> get_contents_from_database() throws Exception {

        ObservableList<Contact> list =
                FXCollections.observableArrayList();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM contacts ORDER BY name ASC"
        );

//        contacts_from_db = FXCollections.observableArrayList();


        while (rs.next()) {

            Contact contact = new Contact(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("category"),
                    rs.getString("address"),
                    rs.getString("company"),
                    rs.getString("notes"),
                    rs.getBoolean("favorite")
            );

            list.add(contact);
        }
//        contacts_list = new ListView<>( contacts_from_db);

//        System.out.println(contacts_from_db.getFirst().name);
        rs.close();
        st.close();

        return list;

    }
}


// BASE CLASS (Contact) ==================
class Contact {
    int id;
    String name,phoneNumber,email, category,address, company,notes;
    boolean favorite;

    public Contact(int id, String name, String phoneNumber, String email, String category, String address, String company, String notes, boolean favorite) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
        this.address = address;
        this.company = company;
        this.notes = notes;
        this.favorite = favorite;
    }

    public void updateContact(String name, String phoneNumber, String email, String category) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
    }

    @Override
    public String toString() {
        return  "  " +name + '\n' +
                "  " + phoneNumber ;
    }
}
