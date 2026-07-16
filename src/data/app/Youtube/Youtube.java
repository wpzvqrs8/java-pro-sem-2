package data.app.Youtube;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Youtube extends Application {
    @FXML
    private WebView youtube;
    @FXML
    public void initialize() {
//        System.out.println("inside init");
        youtube.getEngine().setUserAgent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1"
        );

        youtube.setZoom(0.75);
        youtube.getEngine().load("https://www.youtube.com");

    }

    private Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("youtube.fxml"));

        Parent root = loader.load();
        scene= new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    static void main() {
        launch();
    }
}
