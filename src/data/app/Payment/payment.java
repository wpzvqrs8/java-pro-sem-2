package data.app.Payment;

import data.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;
import java.util.Objects;

public class payment extends Application {

    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    @FXML
    ListView<String> result_list;
    @FXML
    ListView<String> trans_history_list;
    @FXML
    TextField search_bar;

    public static String name_s,phoneNumber_s,country_code_s,upi_id_s,upi_password_s;

    public static void get_user_details() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\Admin\\IdeaProjects\\Java-2_Project\\src\\data\\data\\user_login_data.txt","rw");
        name_s=raf.readLine();
        phoneNumber_s = raf.readLine();
        upi_id_s = raf.readLine();
        upi_password_s = raf.readLine();
        System.out.println(name_s+upi_id_s+upi_password_s);
    }



    ObservableList<String> results = FXCollections.observableArrayList();
    ObservableList<String> transactions = FXCollections.observableArrayList();

    static {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
    stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("payment.fxml")))));
        stage.show();
    }
    static void main() throws IOException {
        get_user_details();
    }
    @FXML
    public void initialize(){
        if (trans_history_list != null) {
            try {
                get_transactions_db();
            } catch (Exception e) { }
        }
    }
    @FXML
    public void show_results(){
//A.I.
        results.clear();

        String search = search_bar.getText();

        String sql = """
            SELECT name, mobile, upi_id
            FROM users
            WHERE name ILIKE ?
            OR mobile ILIKE ?
            OR upi_id ILIKE ?
            """;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String data =
                        rs.getString("name") + " | "
                                + rs.getString("mobile") + " | "
                                + rs.getString("upi_id");

                results.add(data);
            }

            result_list.setItems(results);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void pay_screen_button(ActionEvent event) throws IOException {

    }
    @FXML
    public void open_transactions(ActionEvent event) throws IOException, SQLException {

        System.out.println("t");
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("transaction_history.fxml")));
        stage.setScene(new Scene(root));
//        Main.MAIN_SCENE.getChildren().setAll(root);
    }
    @FXML
    public void open_payment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("payment.fxml")));
        stage.setScene(new Scene(root));
//        Main.MAIN_SCENE.getChildren().setAll(root);
        System.out.println("pay");
    }
    void get_transactions_db() throws Exception {
        get_user_details();
        String  qry = "select * from transactions where from_upi = '"+upi_id_s+"' OR to_upi = '"+upi_id_s+"'";
        System.out.println(qry);
        PreparedStatement ps = conn.prepareStatement(qry);
        ResultSet rs = ps.executeQuery();
        transactions.clear();
        while (rs.next()) {
//                              from_user_id INT,
//                              to_user_id INT,
//                              from_upi VARCHAR(50),
//                              to_upi VARCHAR(50),
//                              amount DOUBLE PRECISION NOT NULL,
//                              status VARCHAR(20),     -- SUCCESS, FAILED
//                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            String data =
                    rs.getInt("from_user_id") + " | "+
                            rs.getInt("to_user_id") + " | "
                            + rs.getString("from_upi") + " | "
                            + rs.getString("to_upi")+ " | "
                            + rs.getDouble("amount")+ " | "
                            + rs.getString("status")+ " | "
                            + rs.getTimestamp("created_at");
            if(rs.getString("from_upi").equals(upi_id_s)||rs.getString("to_upi").equals(upi_id_s))
            transactions.add(data);
        }
        trans_history_list.setItems(transactions);
    }

}
