package data.app.Clock;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldClockController {

    @FXML
    private VBox cityBox;

    @FXML
    private ImageView globeImage;

    private final Map<String, Label> cityLabels = new HashMap<>();

    private final Map<String, ZoneId> cities = new HashMap<>();

    @FXML
    public void initialize() {

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

    }

}
