package data.app.Messages;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

import java.util.Random;

public class chat_cell extends Application {
    @FXML
    private Label msg_text;
    @FXML
    public void initialize()  {

    }

    public void setChat_cell(Chat m){

        msg_text.setText(m.msg);

    }


    @Override
    public void start(Stage stage) throws Exception {

    }
}


class ChatCell extends ListCell<Chat> {

    @Override
    protected void updateItem(Chat c, boolean empty) {
        super.updateItem(c, empty);

        if (empty || c == null) {
            setGraphic(null);
            setText(null);
        } else {

            try {

                FXMLLoader loader ;

                if(c.is_sent) loader = new FXMLLoader(getClass().getResource("to_chat_cell.fxml"));
                else loader = new FXMLLoader(getClass().getResource("from_chat_cell.fxml"));

                Parent root = loader.load();
                root.setLayoutX(25);
                data.app.Messages.chat_cell controller = loader.getController();
                controller.setChat_cell(c);

                setGraphic(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}