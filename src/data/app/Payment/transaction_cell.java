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


    String get_random_hex(){
        String[] fx_colors = {"#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5","#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5","#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5"};
        return fx_colors[new Random().nextInt(fx_colors.length)];
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
    String  to_user_id,  to_upi, amount ;

    public Transaction(String to_user_id, String to_upi, String amount) {
        this.to_user_id = to_user_id;
        this.to_upi = to_upi;
        this.amount = amount;
    }
}