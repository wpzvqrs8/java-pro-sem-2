package data.app.G_Mail;

import data.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class G_Mail  extends Application {
    @FXML
    private WebView g_mail;
    @FXML
    public void initialize() {
        System.out.println("inside init");
        g_mail.getEngine().setUserAgent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1"
        );

        g_mail.setZoom(0.75);
        g_mail.getEngine().load("https://mail.google.com");
        g_mail.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                g_mail.getEngine().executeScript("""
        var style = document.createElement('style');
        style.innerHTML = `
            ::-webkit-scrollbar {
                display: none;
            }
        `;
        document.head.appendChild(style);
        """);
            }
        });


    }

    private Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("G_Mail.fxml"));

        Parent root = loader.load();
        scene= new Scene(root);
        stage.setScene(scene);

        stage.show();
    }
}
