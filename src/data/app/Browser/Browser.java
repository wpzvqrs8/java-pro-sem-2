package data.app.Browser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.Objects;

public class Browser extends Application {

    @FXML
    AnchorPane browser_home_page ;
    @FXML
    TextField url_text;
    @FXML
    WebView  browser_web_view;
    @FXML
    ProgressBar progressBar;
    @FXML
    public void initialize() {
        progressBar.setStyle(
                "-fx-accent: #00ff66;"
        );
        url_text.setStyle(
            "-fx-text-fill: white;" +
                    "-fx-background-color: black;"+
                    "-fx-background-radius: 10;"
    );

        progressBar.setVisible(true);
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        browser_web_view.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

            switch (newState) {

                case RUNNING:
                    progressBar.setVisible(true);
                    break;

                case SUCCEEDED:
                case FAILED:
                case CANCELLED:
                    progressBar.setVisible(false);
                    break;
            }
        });

        browser_web_view.setZoom(0.75);

        Timeline t = new Timeline(
                new KeyFrame(Duration.millis(300), e -> {
                    progressBar.setVisible(false);
                })
        );
    }
    //google.com
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("browser_home_page.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    static void main() {
        launch();
    }
    @FXML
    void browser_menu(ActionEvent event){

    }
    @FXML
    void open_url(ActionEvent event){
        String input = url_text.getText();
        String url = "";
        if (input.startsWith("http://") || input.startsWith("https://")) {url = input;}
        else if (input.contains(".")) {url = "https://" + input;}
        else url = "https://www.google.com/search?q=" + input;
        browser_web_view.getEngine().load(url);
        progressBar.progressProperty().bind(browser_web_view.getEngine().getLoadWorker().progressProperty());
    }

}
