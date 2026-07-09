package data.app.Contacts;

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

public class contact_cell{

    @FXML
    Label intital_avatar,display_name,display_no,is_fav;
    public void set_contact_cell(Contact c){
        intital_avatar.setStyle("-fx-font-size:16px; -fx-background-radius: 50; -fx-border-radius: 50; -fx-border-color:black; -fx-background-color:"+get_random_hex()+";");

        display_name.setStyle("-fx-font-size:16px;");
        display_no.setStyle("-fx-font-size:12px;");
        if(c.favorite) is_fav.setVisible(true);
        is_fav.setStyle("-fx-text-fill:gold;-fx-font-size:20px;");
        try {
            String  s[] = c.name.split(" ");
            intital_avatar.setText("" + s[0].charAt(0) + s[1].charAt(0));
        }
        catch (Exception e){
            intital_avatar.setText(""+c.name.charAt(0));
        }
        display_name.setText(c.name);
        display_no.setText(c.phoneNumber);
    }

    String get_random_hex(){
        String[] fx_colors = {"#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5","#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5","#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5"};
        return fx_colors[new Random().nextInt(fx_colors.length)];
    }
}


//A.I.
class ContactCell extends ListCell<Contact> {

    @Override
    protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);

        if (empty || contact == null) {
            setGraphic(null);
            setText(null);
        } else {

            try {

                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("contact_cell.fxml"));

                Parent root = loader.load();
                root.setLayoutX(30);
                contact_cell controller = loader.getController();
                controller.set_contact_cell(contact);

                setGraphic(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}