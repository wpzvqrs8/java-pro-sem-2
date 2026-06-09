package data;

import data.app.Tic_Tac_Toe.Tic_Tac_Toe;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;


public class Main extends Application {

    Stage stage;
    @FXML
    Label header;
    @FXML
    Label header1;
    @FXML
    ImageView wallpaper;
    @FXML
    private StackPane main_scene;
    static Parent home_screen_root ;

    public static StackPane MAIN_SCENE;
    public static StackPane prev;


    @FXML
    public void initialize() {

        MAIN_SCENE = main_scene;
//
//        System.out.println(Font.getFamilies().contains("Segoe Fluent Icons"));
//        java.time.LocalTime time = java.time.LocalTime.now();
//        header.setStyle("-fx-font-family: 'Segoe Fluent Icons'; -fx-font-size: 18;");
//        header.setText(time.getHour()%12 + ":" + time.getMinute()+"           \uEC37\uEC38\uEC39\uEC3A\uEC3B\uEC3C\uEC3D\uEC3E\uEC3F\uEC40\uEC41 ");

        Font iconFont = Font.loadFont(
                getClass().getResourceAsStream("/data/Segoe Fluent Icons.ttf"),
                18
        );

        LocalTime time = LocalTime.now();
        System.out.println("\uEC37 \uEC38 \uEC39 \uEC3A \uEC3B \uEC3C \uEC3D \uEC3E \uEC3F \uEC40 \uEC41 \uF5F2\uF5F3\uF5F4\uF5F5\uF5F6\uF5F7\uF5F8\uF5F9\uF5FA\uF5FB\uF5FC\uF5FD\uF5FE\uF5FF\uF600\uF601\uF602\uF603\uF604\uF605\uF606\uF607");
        header.setFont(iconFont);
        header.setText("               \uEC3B \uEC3F \uF5FC");
        header1.setFont(Font.font(15));
        header1.setText((time.getHour() % 12 == 0 ? "  12 : " : String.valueOf("  " +time.getHour() % 12+" : "))+  (time.getMinute()<10?"0"+time.getMinute():time.getMinute()));

        Image wallpaper_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/data/data/sys_data/wallpaper.jpg")));
        wallpaper.setImage(wallpaper_img);
    }

    static void main() {
        launch();
//
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("home_screen.fxml"));

        Parent root = loader.load();
        home_screen_root = root;

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
//
//    @FXML
//    void ttt() throws IOException {
////
////        main_scene = new Tic_Tac_Toe().open();
//////        stage.initStyle(StageStyle.UNDECORATED);
////        stage.setScene(main_scene);
//////        stage.show/()
//
//        Parent root = FXMLLoader.load(
//                getClass().getResource("/data/app/Tic_Tac_Toe/loading_screen.fxml"));
//
//        System.out.println("C instance = " + this);
//        stage.setScene(new Scene(root));
//    }
    @FXML
    void back_btn(){

    }
    @FXML
    void home_btn() throws IOException {
//        FXMLLoader loader =
//                new FXMLLoader(getClass().getResource("home_screen.fxml"));
//
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();


    }
    @FXML
    void recent_btn(){

    }

    @FXML
    void ttt(ActionEvent event) throws IOException {



//
//        Parent root = FXMLLoader.load(
//                Tic_Tac_Toe.class.getResource("loading_screen.fxml"));
//
//        main_scene.getChildren().setAll(root);
        Parent root = FXMLLoader.load(
                Tic_Tac_Toe.class.getResource("loading_screen.fxml"));

        MAIN_SCENE.getChildren().setAll(root);
    }
}