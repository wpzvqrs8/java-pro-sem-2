package data.app.Clock;

import data.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class WorldClockController {

    @FXML
    private VBox cityBox;
    @FXML
    private StackPane rootPane;

    @FXML
    private VBox cityDialog;

    @FXML
    private ComboBox<String> cityCombo;
    @FXML
    private ImageView globeImage;

    private final Map<String, Label>   cityLabels = new HashMap<>();

    private final Map<String, ZoneId> cities = new HashMap<>();
    @FXML private StackPane globePane;
    @FXML private SubScene globeSubScene;
    @FXML private Group globeGroup;
    @FXML private Group globeRotationGroup;
    @FXML private Sphere globeSphere;
    @FXML private Rotate globeRotateY;
    @FXML private Rotate globeRotateX;
    @FXML private PerspectiveCamera globeCamera;

    @FXML
    public void initialize() {
        Timeline spin = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(globeRotateY.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(12), new KeyValue(globeRotateY.angleProperty(), 360))
        );
        spin.setCycleCount(Animation.INDEFINITE);
        spin.play();
        cities.put("Ahmedabad", ZoneId.of("Asia/Kolkata"));
        cities.put("London", ZoneId.of("Europe/London"));
        cities.put("New York", ZoneId.of("America/New_York"));
        cities.put("Tokyo", ZoneId.of("Asia/Tokyo"));
        cities.put("Sydney", ZoneId.of("Australia/Sydney"));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateTimes())
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    private void confirmCity() {

        String city = cityCombo.getValue();

        if (city != null && !cityLabels.containsKey(city)) {

            Label label = new Label();
            label.setStyle("-fx-font-size:16px;");

            cityLabels.put(city, label);
            cityBox.getChildren().add(label);

            updateTimes();
        }
        closeCityDialog();
//        cityDialog.setVisible(false);
//        cityDialog.setManaged(false);
    }
    @FXML
    private void handleGlobeClick() {

        cityCombo.getItems().setAll(cities.keySet());
        cityCombo.setValue("Ahmedabad");
        cityDialog.setLayoutX(75);
        cityDialog.setLayoutY(125);

        cityDialog.toFront();
        cityDialog.setManaged(true);
        cityDialog.setVisible(true);
        cityDialog.setManaged(true);
        cityDialog.setVisible(true);
//

    }
    @FXML
    private void closeCityDialog() {
        cityDialog.setVisible(false);
        cityDialog.setManaged(false);
    }


    private void updateTimes(){

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        for(String city : cityLabels.keySet()){

            LocalTime time = java.time.ZonedDateTime.now(cities.get(city)).toLocalTime();

            cityLabels.get(city)
                    .setText(city + "    " + time.format(formatter));

        }

    }

    @FXML
    private void backHome() throws IOException {

        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Clock.class.getResource("Clock.fxml")));
        root.setLayoutY(25);
        Main.MAIN_SCENE.getChildren().setAll(root);

    }

}
