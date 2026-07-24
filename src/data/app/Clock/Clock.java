package data.app.Clock;

import data.Main;
import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Clock extends Application {

    @FXML
    private Label lblTime;
    @FXML
    private Label notificationLabel;
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
                new SpinnerValueFactory.IntegerSpinnerValueFactory(LocalDateTime.now().getHour(), 23,0));

        spMinute.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(LocalDateTime.now().getMinute()+1, 59,0));

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

                    notificationLabel.setText("Alarm Time Reached!");
                    notificationLabel.setVisible(true);

                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
pause.setOnFinished(e->notificationLabel.setVisible(false));

                    pause.play();
                }

            }

        }

    }
    @FXML
    private void openWorldClock() throws IOException {

        Parent root = FXMLLoader.load(
                Objects.requireNonNull(WorldClockController.class.getResource("worldClock.fxml")));
        root.setLayoutY(25);

        Main.MAIN_SCENE.getChildren().setAll(root);
        Main.recent_apps_stack.pop();
        Main.recent_apps_stack.push(root);
    }

    @FXML
    private void openTimer() throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(TimerController.class.getResource("Timer.fxml")));
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);
        Main.recent_apps_stack.pop();
        Main.recent_apps_stack.push(root);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Clock.fxml"));

        Scene scene = new Scene(loader.load());

        // Load CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Clock App");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}