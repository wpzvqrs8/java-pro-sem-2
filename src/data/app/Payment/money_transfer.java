package data.app.Payment;

import data.Main;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;
import java.util.Objects;


public class money_transfer extends Application {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    private  double balance = 0.0;
    private  double debit = 0.0;
    @FXML
    Label to_user_name,amount_letters_label,from_name,avatar;
    @FXML
    TextField amount_text_field;
    @FXML
    VBox insufficientBalance_warning,invalid_digit_amount;
    @FXML
    Button submit;
    static String upi ;
    static String from_upi ;

    static {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void initialize() {
        to_user_name.setText(upi);
        from_name.setText("  From - "+from_upi);
        avatar.setText(""+upi.charAt(0));
    }


    @Override
    public void start(Stage stage) throws Exception {

    }
    @FXML
    void pay() throws Exception {
        if(get_amount()){
        System.out.println("yes");
        String  credit_qry = "update users set balance = ? where upi_id = ?";
        PreparedStatement to_user_qry = conn.prepareStatement(credit_qry);
        String  debit_qry = "update users set balance = ? where upi_id = ?";
        PreparedStatement from_user_qry = conn.prepareStatement(debit_qry);
        debit = Double.parseDouble(amount_text_field.getText());
            System.out.println();
            System.out.println(balance+debit);
            System.out.println(balance-debit);
            System.out.println();
        from_user_qry.setDouble(1,balance+debit);
        from_user_qry.setString(2,upi);
        to_user_qry.setDouble(1,balance-debit);
        to_user_qry.setString(2,from_upi);
            from_user_qry.executeUpdate();
            to_user_qry.executeUpdate();

            String  insert_tscn_user = "insert into transactions(from_user_id, to_user_id, from_upi, to_upi, amount, status)    VALUES ((SELECT user_id FROM users WHERE upi_id = ?),(SELECT user_id FROM users WHERE upi_id = ?),?, ?, ?, ?)";
            PreparedStatement tscn_u = conn.prepareStatement(insert_tscn_user);
            tscn_u.setString(1,from_upi);
            tscn_u.setString(2,upi);
            tscn_u.setString(3,from_upi);
            tscn_u.setString(4,upi);
            tscn_u.setDouble(5,debit);
            tscn_u.setString(6,"SUCCESS");
            tscn_u.executeUpdate();



            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("transaction_history.fxml")));
            root.setLayoutY(25);
            Main.MAIN_SCENE.getChildren().setAll(root);
            Main.prev_screen_stack.push(root);

        }
        else {
            System.out.println("err-2");
            insufficientBalance_warning.setVisible(true);
            PauseTransition ptt = new PauseTransition(Duration.seconds(2));
            ptt.setOnFinished(e->insufficientBalance_warning.setVisible(false));
            ptt.play();
        }
    }
    @FXML
    boolean get_amount() throws Exception {

        if(!amount_text_field.getText().toUpperCase().equals(amount_text_field.getText().toLowerCase()) || Double.parseDouble(amount_text_field.getText())<=0.0){

            invalid_digit_amount.setVisible(true);
            PauseTransition pt = new PauseTransition(Duration.seconds(2));
            pt.setOnFinished(e->{
                invalid_digit_amount.setVisible(false);
            });
            pt.play();

        }
        else if (check_balance(upi)) {
            amount_letters_label.setText(amount_text_field.getText()+" -/ only");
            return true;
        }
        return false;
    }
    private  boolean check_balance(String u) throws Exception {

        String  qry = "select * from users where upi_id = ?";
        PreparedStatement ps = conn.prepareStatement(qry);

        ps.setString(1,from_upi);

//        System.out.println(qry);
        ResultSet rs = ps.executeQuery();
//        transactions.clear();
        while (rs.next()) {
            System.out.println("fu-"+rs.getDouble("balance"));
//                              from_user_id INT,
//                              to_user_id INT,
//                              from_upi VARCHAR(50),
//                              to_upi VARCHAR(50),
//                              amount DOUBLE PRECISION NOT NULL,
//                              status VARCHAR(20),     -- SUCCESS, FAILED
//                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

            balance  = rs.getDouble("balance");
            if(rs.getDouble("balance")>=Double.parseDouble(amount_text_field.getText())) {

                return true;
            }


        }
        return  false;

    }
}
