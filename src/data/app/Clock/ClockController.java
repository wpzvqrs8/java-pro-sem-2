package data.app.Clock;

import java.awt.Toolkit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockController {

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    @FXML
    private Spinner<Integer> spHour;

    @FXML
    private Spinner<Integer> spMinute;

    @FXML
    private Button btnAddAlarm;

    @FXML
    private ListView<Alarm> alarmList;

    private ObservableList<Alarm> alarms =
            FXCollections.observableArrayList();

    private Timeline clockTimeline;

    @FXML
    public void initialize() {

        alarmList.setItems(alarms);

        spHour.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0));

        spMinute.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0));

        updateClock();

        clockTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateClock())
        );

        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }

    private void updateClock() {

        LocalDateTime now = LocalDateTime.now();

        lblTime.setText(
                now.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );

        lblDate.setText(
                now.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy"))
        );

        alarmList.refresh();

        checkAlarms();
    }

    @FXML
    private void addAlarm() {

        int hour = spHour.getValue();

        int minute = spMinute.getValue();

        LocalDateTime alarmTime =
                LocalDate.now().atTime(hour, minute);

        if (alarmTime.isBefore(LocalDateTime.now())) {

            alarmTime = alarmTime.plusDays(1);

        }

        alarms.add(new Alarm(alarmTime));

    }

    private void checkAlarms() {

        for (Alarm alarm : alarms) {

            if (alarm.isActive()) {

                LocalDateTime now = LocalDateTime.now();

                if (!now.isBefore(alarm.getAlarmTime())) {

                    Toolkit.getDefaultToolkit().beep();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Alarm");

                    alert.setHeaderText(null);

                    alert.setContentText("Alarm Time Reached!");

                    alert.show();

                    alarm.stop();

                }

            }

        }

    }
    @FXML
    private void openWorldClock() {

        changeScene("WorldClock.fxml");

    }

    @FXML
    private void openTimer() {

        changeScene("Timer.fxml");

    }

    private void changeScene(String fxmlFile) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(fxmlFile));

            Parent root = loader.load();

            Stage stage =
                    (Stage) btnAddAlarm.getScene().getWindow();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass().getResource("style.css").toExternalForm()
            );

            stage.setScene(scene);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}