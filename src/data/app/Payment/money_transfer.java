package data.app.Payment;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;


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
        to_user_name.setText(upi.substring(0,upi.indexOf(".")));
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
        String  qry = "update users set balance = ? where upi_id = ?";
        PreparedStatement ps = conn.prepareStatement(qry);

        ps.setString(2,upi.substring(upi.lastIndexOf(".")+1,upi.length()));
        debit = Double.parseDouble(amount_text_field.getText());
        System.out.println("d-"+debit);
        ps.setDouble(1,balance-debit );
        System.out.println("bd"+(balance-debit));
        System.out.println(ps.executeUpdate());
        }
    }
    @FXML
    boolean get_amount() throws Exception {
        if(!amount_text_field.getText().toUpperCase().equals(amount_text_field.getText().toLowerCase())){
            invalid_digit_amount.setVisible(true);
            PauseTransition pt = new PauseTransition(Duration.seconds(3));
            pt.setOnFinished(e->{
                invalid_digit_amount.setVisible(false);
            });
            pt.play();

        }
        else if (!check_balance(upi)) {
            amount_letters_label.setText(amount_text_field.getText()+" -/ only");
            return true;
        }
        return false;
    }
    private  boolean check_balance(String u) throws Exception {

        String  qry = "select * from users where upi_id = ?";
        PreparedStatement ps = conn.prepareStatement(qry);
        System.out.println("--"+u.substring(u.lastIndexOf("."),u.length()));
        ps.setString(1,u.substring(u.lastIndexOf(".")+1,u.length()));

//        System.out.println(qry);
        ResultSet rs = ps.executeQuery();
//        transactions.clear();
        while (rs.next()) {
//                              from_user_id INT,
//                              to_user_id INT,
//                              from_upi VARCHAR(50),
//                              to_upi VARCHAR(50),
//                              amount DOUBLE PRECISION NOT NULL,
//                              status VARCHAR(20),     -- SUCCESS, FAILED
//                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            System.out.println(rs.getDouble("balance"));
            balance  = rs.getDouble("balance");
            if(rs.getDouble("balance")>=Double.parseDouble(amount_text_field.getText()))
                return true;


        }
        return  false;

    }
}
