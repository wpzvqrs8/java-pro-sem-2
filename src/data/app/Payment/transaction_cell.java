package data.app.Payment;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class transaction_cell{
    @FXML
    Label display_upi_id,display_amount;
    void set_transaction_cell(String upi, String amount , boolean is_paid){
        display_upi_id.setText(upi);
        display_amount.setText(amount);
        if(is_paid){
            display_amount.setStyle("-fx-background-color:green");
        }
        else{
            display_amount.setStyle("-fx-background-color:red");
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
                c.set_transaction_cell(t.to_upi,t.amount,t.is_paid);
                root.setLayoutX(30);
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
    boolean is_paid;

    public Transaction(String to_upi, String amount ,boolean is_paid) {
        this.is_paid = is_paid;
        this.to_upi = to_upi;
        this.amount = amount;
    }
}