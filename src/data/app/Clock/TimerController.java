package data.app.Clock;

<<<<<<< HEAD
=======
import data.Main;
>>>>>>> 28f9434 (clock . gallery . phone)
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.Objects;
>>>>>>> 28f9434 (clock . gallery . phone)

public class TimerController {

    @FXML
    private Spinner<Integer> spMinute;

    @FXML
    private Spinner<Integer> spSecond;

    @FXML
    private Label lblTimer;

    private Timeline timeline;

    private int totalSeconds;

    @FXML
    public void initialize() {

        spMinute.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));

        spSecond.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));

        lblTimer.setText("00:00");

    }

    @FXML
    private void startTimer() {

        totalSeconds =
                spMinute.getValue() * 60 + spSecond.getValue();

        if(totalSeconds <= 0)
            return;

        if(timeline != null)
            timeline.stop();

        timeline = new Timeline(

                new KeyFrame(Duration.seconds(1), e -> {

                    totalSeconds--;

                    int minute = totalSeconds / 60;

                    int second = totalSeconds % 60;

                    lblTimer.setText(
                            String.format("%02d:%02d",
                                    minute,
                                    second));

                    if(totalSeconds <= 0){

                        timeline.stop();

                        java.awt.Toolkit.getDefaultToolkit().beep();

                    }

                })

        );

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

    }

    @FXML
    private void stopTimer() {

        if(timeline != null){

            timeline.stop();

        }

    }

    @FXML
    private void resetTimer() {

        if(timeline != null){

            timeline.stop();

        }

        spMinute.getValueFactory().setValue(0);

        spSecond.getValueFactory().setValue(0);

        lblTimer.setText("00:00");

    }

    @FXML
<<<<<<< HEAD
    private void backHome() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("Clock.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) lblTimer.getScene().getWindow();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass().getResource("style.css").toExternalForm());

            stage.setScene(scene);

        }

        catch(IOException e){

            e.printStackTrace();

        }
=======
    private void backHome() throws IOException {

        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Clock.class.getResource("Clock.fxml")));
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);
>>>>>>> 28f9434 (clock . gallery . phone)

    }

}
