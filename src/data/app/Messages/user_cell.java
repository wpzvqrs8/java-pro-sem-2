package data.app.Messages;

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

public class user_cell{

    @FXML
    Label intital_avatar,display_name,display_msg;
    public void set_message_cell(Message m){
        intital_avatar.setStyle("-fx-font-size:16px; -fx-background-radius: 50; -fx-border-radius: 50; -fx-border-color:black; -fx-background-color:"+get_random_hex()+";");
        display_name.setStyle("-fx-font-size:16px; -fx-font-weight: bold;");
        display_msg.setStyle("-fx-font-size:12px;");
        try {
            String  s[] = m.name.split(" ");
            intital_avatar.setText("" + s[0].charAt(0) + s[1].charAt(0));
        }
        catch (Exception e){
            intital_avatar.setText(""+m.name.charAt(0));
        }
        display_name.setText(m.name);
        display_msg.setText(m.last_msg);
    }

    String get_random_hex(){
        String[] fx_colors = {"#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5","#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5","#90ced6","#fa985f","#678de6","#d1a4de","#597ccf","#87dea4","#e3af98","#ed7a07","#ed9a9a","#a0e3f2","#faae82","#61c7a2","#a1f7c9","#eb98a5"};
        return fx_colors[new Random().nextInt(fx_colors.length)];
    }
}


class MessageCell extends ListCell<Message> {

    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);

        if (empty || message == null) {
            setGraphic(null);
            setText(null);
        } else {

            try {

                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("user_cell.fxml"));

                Parent root = loader.load();
                root.setLayoutX(30);
                data.app.Messages.user_cell controller = loader.getController();
                controller.set_message_cell(message);

                setGraphic(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}