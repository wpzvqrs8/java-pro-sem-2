package data.app.Payment;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.sql.*;
import java.util.Objects;

public class upi extends Application {

    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    @FXML
    Button login_button;
    @FXML
    TextField name,phoneNumber,country_code,upi_id,upi_password;
    @FXML
    CheckBox agree_cb;
    @FXML
    HBox invalid_warning_message;
    static boolean valid_values = true;
    static payment payment_app_open  = new payment();
    String name_s,phoneNumber_s,country_code_s,upi_id_s,upi_password_s;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login_screen.fxml"))));
        stage.show();
    }
    static void main() {
        launch();
    }
    static {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize(){
        login_button.setDisable(true);
        country_code_s = "+91";
        invalid_warning_message.setVisible(false);
    }
void show_warning() {
    invalid_warning_message.setVisible(true);
    PauseTransition pause = new PauseTransition(Duration.seconds(3));
    pause.setOnFinished(e -> {
        invalid_warning_message.setVisible(false);
    });
    pause.play();

//    invalid_warning_message.setVisible(false);
    System.out.println("warning");
}
    @FXML
    public void login(ActionEvent event) throws Exception {
        if(!valid_values){
           show_warning();
            name.requestFocus();
        }
        else {
//            A.I. as i fel lzy

            String sql = "INSERT INTO users (name, mobile, upi_id, pin) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name_s);
            ps.setString(2, country_code_s + phoneNumber_s);
            ps.setString(3, upi_id_s);
            ps.setString(4, upi_password_s);
            System.out.println(ps.executeUpdate()>0?"done":"err");
         payment_app_open.open_transactions(event);
//         payment_app_open.set_user_details(name_s,phoneNumber_s,country_code_s,upi_id_s,upi_password_s);
            RandomAccessFile raf = new RandomAccessFile("C:\\Users\\Admin\\IdeaProjects\\Java-2_Project\\src\\data\\data\\user_login_data.txt","rw");


            raf.write((name_s+"\n").getBytes());
            raf.write((country_code_s+phoneNumber_s+"\n").getBytes());
            raf.write((upi_id_s+"\n").getBytes());
            raf.write((upi_password_s).getBytes());
        }
    }
    @FXML
    public  void save_name(ActionEvent event){
        if(name.getText().length()<=50 && name.getText().length()>0){
            valid_values = true;
            name_s=name.getText();
            phoneNumber.requestFocus();
        }
        else {
            show_warning();
            valid_values = false;
        }
//        System.out.println(" - "+valid_values);
    }
    @FXML
    public  void save_cc(ActionEvent event){

        if(country_code.getText().length()<=3 && !country_code.getText().isEmpty()){
            valid_values = true;
            country_code_s = country_code.getText();
            phoneNumber.requestFocus();
        }
        else {
            show_warning();
            invalid_warning_message.setVisible(true);
            valid_values = false;
        }
        System.out.println(" - "+valid_values);
    }
    @FXML
    public  void save_mobilenumber(ActionEvent event){
        if(phoneNumber.getText().length()==10 && (phoneNumber.getText().toLowerCase().equals(phoneNumber.getText().toUpperCase())) && phoneNumber.getText().length()>0){
            valid_values = true;
            phoneNumber_s=phoneNumber.getText();
            upi_id.requestFocus();
        }
        else {show_warning();
            valid_values = false;
        }
        System.out.println(" - "+valid_values);
    }
    @FXML
    public  void save_upi_id(ActionEvent event){
        if(upi_id.getText().length()<=50 && upi_id.getText().contains("@")&&(upi_id.getText().lastIndexOf("@")<upi_id.getText().length()-1) && upi_id.getText().length()>0){
            valid_values = true;
            upi_id_s=upi_id.getText();
            upi_password.requestFocus();
        }
        else {show_warning();
            valid_values = false;
        }
//        System.out.println(" - "+valid_values);
    }
    @FXML
    public  void save_upi_password(ActionEvent event){
        if(upi_password.getText().length()==4 && (upi_password.getText().toUpperCase().equals(upi_password.getText().toLowerCase())) && upi_password.getText().length()>0){
            valid_values = true;
            upi_password_s=upi_password.getText();
            agree_cb.requestFocus();
        }
        else {
            show_warning();
            valid_values = false;
        }
    }
    @FXML
    public  void is_agreed(ActionEvent event) throws Exception {

        if(agree_cb.isSelected() && valid_values) {
            login_button.setDisable(false);
            login_button.requestFocus();
        }
        else {
            login_button.setDisable(true);
            valid_values = false;
        }
    }

}
