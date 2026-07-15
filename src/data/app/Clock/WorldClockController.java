package data.app.Clock;

<<<<<<< HEAD
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
=======
import data.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
>>>>>>> 28f9434 (clock . gallery . phone)
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
<<<<<<< HEAD
=======
import java.util.Objects;
>>>>>>> 28f9434 (clock . gallery . phone)
import java.util.Optional;

public class WorldClockController {

    @FXML
    private VBox cityBox;
<<<<<<< HEAD

    @FXML
=======
    @FXML
    private StackPane rootPane;

    @FXML
    private VBox cityDialog;

    @FXML
    private ComboBox<String> cityCombo;
    @FXML
>>>>>>> 28f9434 (clock . gallery . phone)
    private ImageView globeImage;

    private final Map<String, Label> cityLabels = new HashMap<>();

    private final Map<String, ZoneId> cities = new HashMap<>();
<<<<<<< HEAD

    @FXML
    public void initialize() {

=======
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
>>>>>>> 28f9434 (clock . gallery . phone)
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
<<<<<<< HEAD

    @FXML
    private void handleGlobeClick() {

        ChoiceDialog<String> dialog =
                new ChoiceDialog<>("Ahmedabad", cities.keySet());

        dialog.setTitle("World Clock");
        dialog.setHeaderText("Select a City");

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){

            String city = result.get();

            if(!cityLabels.containsKey(city)){

                Label label = new Label();

                label.setStyle("-fx-font-size:16;");

                cityLabels.put(city,label);

                cityBox.getChildren().add(label);

            }

            updateTimes();

        }

    }
=======
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

>>>>>>> 28f9434 (clock . gallery . phone)

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
<<<<<<< HEAD
    private void backHome(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("Clock.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) cityBox.getScene().getWindow();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass().getResource("style.css").toExternalForm()
            );

            stage.setScene(scene);

        }catch(IOException e){

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
class time_zones{
    LocalTime local_time;
    String place;

    public time_zones(LocalTime local_time, String place) {
        this.local_time = local_time;
        this.place = place;
    }
}