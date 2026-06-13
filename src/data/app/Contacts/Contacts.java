package data.app.Contacts;

import data.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
    TextField name,phoneNumber,email, category, company,notes, address;
    @FXML
    CheckBox favorite;
    @FXML
    Button save_new_button;
    Contact new_contact;// local contact..

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

    Parent root = null;
    static Stage stage ;
    @Override
    public void start(Stage stage1) {
        Scene scene;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("contacts_main_frame.fxml")));

        } catch (IOException e) {
        }
         scene = new Scene(root);
        stage = new Stage();
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



    @FXML
    void add_contact(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("add_new_contact.fxml"))
        );

        contacts_list.getScene().setRoot(root);


    }
     @FXML
    void save_new_contact(ActionEvent event){


    }
    @FXML
    private void save_object(ActionEvent event) {
        Object source = event.getSource();
// add to local object - Contact new_contact
        new_contact  = new Contact();
        if (source == name) {
            // name
            new_contact.name = ((TextField)source).getText();
            // do same for all the fields ...
            phoneNumber.requestFocus();
        } else if (source == phoneNumber) {
            email.requestFocus();
        } else if (source == email) {
            category.requestFocus();
        }
        else if (source == company) {
            notes.requestFocus();
        } else if (source == address) {
            company.requestFocus();
        }
        else if (source == category) {
            address.requestFocus();
        } else if (source == notes) {
            favorite.requestFocus();
        } else if (source == favorite) {
            save_new_button.requestFocus();
        }

        try {
            System.out.println(((TextField)source).getId()+"-"+((TextField)source).getText());
        }catch (Exception e){
            System.out.println(((CheckBox)source).getId()+"-"+((CheckBox)source).isSelected());
        }

    }

}


// BASE CLASS (Contact) ==================>
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

    public Contact() {
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
