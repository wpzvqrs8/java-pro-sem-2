package data.app.Payment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

        List<String> ls = Files.readAllLines(Path.of("C:\\Users\\Admin\\IdeaProjects\\Java-2_Project\\src\\data\\data\\user_login_data.txt"));
//        System.out.println(name_s+phoneNumber_s);
        System.out.println(ls);
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
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("transaction_history.fxml")))));

    }
    @FXML
    public void open_payment(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("payment.fxml")))));
        System.out.println("pay");
    }
    void get_transactions_db() throws SQLException {

        PreparedStatement ps = conn.prepareStatement("select * from transactions");
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
