package referances;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AndroidFooterDemo extends Application {

    @Override
    public void start(Stage stage) {


        // Load images
        Image backImage = new Image("file:C:/Users/admin/Downloads/back.png");
        Image homeImage =new Image("file:C:/Users/admin/Downloads/back.png");
        Image recentImage = new Image("file:C:/Users/admin/Downloads/back.png");

        // Create image views
        ImageView backIcon = new ImageView(backImage);
        backIcon.setFitWidth(40);
        backIcon.setFitHeight(40);

        ImageView homeIcon = new ImageView(homeImage);
        homeIcon.setFitWidth(40);
        homeIcon.setFitHeight(40);

        ImageView recentIcon = new ImageView(recentImage);
        recentIcon.setFitWidth(40);
        recentIcon.setFitHeight(40);

        // Create buttons
        Button backBtn = new Button();
        backBtn.setGraphic(backIcon);

        Button homeBtn = new Button();
        homeBtn.setGraphic(homeIcon);

        Button recentBtn = new Button();
        recentBtn.setGraphic(recentIcon);

        // Footer layout
        HBox footer = new HBox(50, backBtn, homeBtn, recentBtn);
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-padding: 15;");

        BorderPane root = new BorderPane();
        root.setBottom(footer);

        Scene scene = new Scene(root, 400, 700);

        stage.setTitle("Android Footer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}