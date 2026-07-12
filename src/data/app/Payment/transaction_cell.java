package data.app.Payment;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;

public class transaction_cell{
    @FXML
    Label display_upi_id,display_amount,time_stamp,pill_border;
    void set_transaction_cell(String upi, String amount , LocalDateTime time,boolean is_paid){
        display_upi_id.setText(upi);
        time_stamp.setText(time.format(DateTimeFormatter.ofPattern("dd-MMMM-yy HH:mm:ss ")));
        display_amount.setText(amount+"₹");
        time_stamp.setStyle("-fx-font-size: 12px; -fx-font-family: 'Arial';");
            display_upi_id.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial';");

        if(is_paid){
            pill_border.setStyle("-fx-border-color:green;-fx-background-radius: 9999; -fx-border-radius: 9999;  -fx-border-width: 2; -fx-background-color: null;");
            display_amount.setStyle("-fx-text-fill:green;-fx-font-size: 14px; -fx-font-family: 'Arial';-fx-background-radius: 9999;-fx-border-color:green; -fx-border-radius: 9999;  -fx-border-width: 2; -fx-background-color: black;");

        }
        else{
            pill_border.setStyle("-fx-border-color:red; -fx-background-radius: 9999; -fx-border-radius: 9999;  -fx-border-width: 2; -fx-background-color: null;");
            display_amount.setStyle("-fx-text-fill:red; -fx-font-size: 14px; -fx-font-family: 'Arial';-fx-background-radius: 9999;-fx-border-color:red; -fx-border-radius: 9999;  -fx-border-width: 2; -fx-background-color: black;");
        }
    }
}


//A.I.
class TransactionCell extends ListCell<Transaction> {

    @Override
    protected void updateItem(Transaction t, boolean empty) {
        super.updateItem(t, empty);

        if (empty || t == null) {
            setGraphic(null);
            setText(null);
        } else {

            try {

                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("transaction_cell.fxml"));
                Parent root = loader.load();
                transaction_cell c = loader.getController();
                c.set_transaction_cell(t.to_upi,t.amount,t.timestamp,t.is_paid);

//                data.app.Contacts.contact_cell controller = loader.getController();
//                controller.set_contact_cell(t);
                setGraphic(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}

class Transaction {
    String    to_upi, amount ;
    LocalDateTime timestamp;
    boolean is_paid;

    public Transaction(String to_upi, String amount ,LocalDateTime timestamp, boolean is_paid) {
        this.is_paid = is_paid;
        this.to_upi = to_upi;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}