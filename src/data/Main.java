package data;
/*

     --module-path "E:\ProgramFiles\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.web --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED --enable-native-access=javafx.media
                ^ your fx folder path
 */
import data.app.Browser.Browser;
import data.app.Calculator.Calculator;
import data.app.Contacts.Contacts;
import data.app.G_Mail.G_Mail;
import data.app.Payment.upi;
import data.app.Tic_Tac_Toe.Tic_Tac_Toe;
import data.app.Youtube.Youtube;
import data.data.sys_data.get_sys_info;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;
/*

 --module-path "E:\ProgramFiles\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.web --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED --enable-native-access=javafx.media
                ^ your fx folder path
 */

public class Main extends Application {

    Stage stage;
    @FXML
    Label header;
    @FXML
    private  Label header1;
    @FXML
    ImageView wallpaper;
    @FXML
    private AnchorPane main_scene;
    @FXML
    private Button back,home,recent;
    public static Parent home_screen_root ;

    private static Parent root;

    static {
        try {
            root = FXMLLoader.load(Main.class.getResource("home_screen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ;
    public static AnchorPane MAIN_SCENE;
    public static AnchorPane temp_pane;

    static LocalTime time = LocalTime.now();
    public static Stack<Parent> prev_screen_stack  = new Stack<>();
    static boolean is_initialised = false;

    public Main() throws IOException {
    }

        @FXML
    public void initialize() {
        MAIN_SCENE = main_scene;
        main_scene.requestFocus();
        if(!is_initialised) {
            is_initialised = true;
            print_sys_msg("System Booted Sucessfully");

            System.out.println(MAIN_SCENE);
        }
//        System.out.println(Font.getFamilies().contains("Segoe Fluent Icons"));
//        java.time.LocalTime time = java.time.LocalTime.now();
//        header.setStyle("-fx-font-family: 'Segoe Fluent Icons'; -fx-font-size: 18;");
//        header.setText(time.getHour()%12 + ":" + time.getMinute()+"           \uEC37\uEC38\uEC39\uEC3A\uEC3B\uEC3C\uEC3D\uEC3E\uEC3F\uEC40\uEC41 ");

            Font iconFont = Font.loadFont(
                    getClass().getResourceAsStream("/data/Segoe Fluent Icons.ttf"),
                    18
            );

//        System.out.println("\uE72B \uE80F \uECA5");
//        back.setFont(iconFont);
//        home.setFont(iconFont);
//        recent.setFont(iconFont);
//        back.setText("\uE72B");
//        home.setText("\uE80F");
//        recent.setText("\uECA5");

            back.setText("◀");
            back.setFont(Font.font(25));
            home.setText("●");
            home.setFont(Font.font(25));
            recent.setText("■");
//        System.out.println("\uEC37 \uEC38 \uEC39 \uEC3A \uEC3B \uEC3C \uEC3D \uEC3E \uEC3F \uEC40 \uEC41 \uF5F2\uF5F3\uF5F4\uF5F5\uF5F6\uF5F7\uF5F8\uF5F9\uF5FA\uF5FB\uF5FC\uF5FD\uF5FE\uF5FF\uF600\uF601\uF602\uF603\uF604\uF605\uF606\uF607");

            header.setFont(iconFont);
            header1.setFont(Font.font(15));
//        header1.setText((time.getHour() % 12 == 0 ? "  12 : " : String.valueOf("  " +time.getHour() % 12+" : "))+  (time.getMinute()<10?"0"+time.getMinute():time.getMinute()));

            Image wallpaper_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/data/data/sys_data/wallpaper.jpg")));
            wallpaper.setImage(wallpaper_img);
//        Timeline clock = new Timeline(
//                new KeyFrame(Duration.seconds(0), e ->
//                {
//                    LocalTime time = LocalTime.now();
//                    header1.setText("  " + (time.getHour() % 12 == 0 ? 12 : time.getHour() % 12) + " : " + String.format("%02d", time.getMinute())+" : " + String.format("%02d", time.getSecond()));
//                }),
//                new KeyFrame(Duration.seconds(1))
//        );
//        clock.setCycleCount(Timeline.INDEFINITE);
//        clock.play();
//        Timeline header_symbols = new Timeline(
//                new KeyFrame(Duration.seconds(0), e ->
//                {
//                    get_sys_info sys_stats  = new get_sys_info();
//
//                    try {
//                        header.setFont(iconFont);
//
//
////                        header.setText("               \uEC3B");
//                        header.setText("             \uEC3B  "+sys_stats.getWifi()+"  "+sys_stats.getBattery());
//
//                    } catch (Exception ex) {
//                        header.setFont(iconFont);
//                        header.setText("             \uEC3B \uEC3F \uF5FC");
//                    }
//                }),
//                new KeyFrame(Duration.seconds(10))
//        );
//        header_symbols.setCycleCount(Timeline.INDEFINITE);
//        header_symbols.play();
//
            //TEMP
            header.setFont(iconFont);
            header.setText("             \uEC3B \uEC3F \uF5FC");
            LocalTime time = LocalTime.now();
            header1.setText("  " + (time.getHour() % 12 == 0 ? 12 : time.getHour() % 12) + " : " + String.format("%02d", time.getMinute()) + " : " + String.format("%02d", time.getSecond()));

    }

    static void main() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

//        prev_screen_stack.push(root);
        home_screen_root = root;
root.requestFocus();
        Scene scene = new Scene(root);

//        System.out.println("prev-"+prev);

//        System.out.println("temp-"+temp_pane);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }


    void show_fxml(Parent root_screen){
        if(!MAIN_SCENE.getChildren().isEmpty()){
            prev_screen_stack.push(root_screen);
        }
        MAIN_SCENE.getChildren().setAll(root_screen);
    }
    @FXML
    void back_btn(ActionEvent event) throws IOException {
        System.out.println(prev_screen_stack);
//        System.out.println(temp_pane);
//        if(prev==null || prev == temp_pane|| prev==home_screen_root){
//
//            Parent root = FXMLLoader.load(
//                    getClass().getResource("home_screen.fxml")
//            );
//            Stage stage = (Stage) ((Button) event.getSource())
//                    .getScene()
//                    .getWindow();
//            stage.getScene().setRoot(root);
//
//            try{
//                prev = (AnchorPane) root;
//                temp_pane = prev;
//                MAIN_SCENE.getChildren().setAll(prev);
//                prev_screen_stack.push(prev);
//            } catch (Exception e) {
//            }
//        }else {
//
//            MAIN_SCENE.getChildren().clear();
//            MAIN_SCENE.getChildren().setAll(prev);
//            System.out.println("done");
//
//        }

 /*       try {
//            MAIN_SCENE.getChildren().clear();
            System.out.println(MAIN_SCENE);

            MAIN_SCENE.getChildren().setAll(prev_screen_stack.pop());
            System.out.println("done");
        } catch (Exception e) {
            MAIN_SCENE.getChildren().setAll(root);
        }*/
        if(!prev_screen_stack.isEmpty()){
//            MAIN_SCENE.getChildren().setAll(prev_screen_stack.pop());


            Parent prevScreen = prev_screen_stack.pop();

            if (prevScreen == root || prevScreen == home_screen_root) {

                MAIN_SCENE.getChildren().clear();
            } else {
                MAIN_SCENE.getChildren().setAll(prevScreen);
            }
        }
        else {
//            MAIN_SCENE.getChildren().clear();
//            MAIN_SCENE.getChildren().setAll(home_screen_root);
            prev_screen_stack.clear();
            Parent root = FXMLLoader.load(
                    getClass().getResource("home_screen.fxml")
            );
//TODO
            Stage stage = (Stage) ((Button) event.getSource())
                    .getScene()
                    .getWindow();

            stage.getScene().setRoot(root);
//        show_fxml(root);
            System.out.println("done");
        }

    }
    @FXML
    void home_btn(ActionEvent event) throws IOException {
        prev_screen_stack.clear();
        Parent root = FXMLLoader.load(
                getClass().getResource("home_screen.fxml")
        );
//TODO
        Stage stage = (Stage) ((Button) event.getSource())
                .getScene()
                .getWindow();

        stage.getScene().setRoot(root);
//        show_fxml(root);
        System.out.println("done");
    }
    @FXML
    void recent_btn(){
//TODO

    }



//    ------------------------------------------------ apps

    @FXML
    void phone(ActionEvent event) throws IOException {
        System.out.println("remaining");
    }
    @FXML
    void contacts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Contacts.class.getResource("contacts_main_frame.fxml")));
//        MAIN_SCENE.getChildren().setAll(root);
        show_fxml(root);

    }

    @FXML
    void message(ActionEvent event) throws Exception{
        System.out.println("remaining");
    }

    @FXML
    void ttt(ActionEvent event) throws IOException {
//
//        Parent root = FXMLLoader.load(
//                Tic_Tac_Toe.class.getResource("loading_screen.fxml"));
//
//        main_scene.getChildren().setAll(root);
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Tic_Tac_Toe.class.getResource("loading_screen.fxml")));
        root.setLayoutY(25);
        show_fxml(root);
//        MAIN_SCENE.getChildren().setAll(root);
    }
    @FXML
    void youtube(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Youtube.class.getResource("youtube.fxml")));

        root.setLayoutY(25);
        MAIN_SCENE.getChildren().setAll(root);
    }

    @FXML
    void gmail(ActionEvent event)throws Exception {
//        System.out.println("remaining");
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(G_Mail.class.getResource("G_Mail.fxml")));
        root.setLayoutY(-20);
        show_fxml(root);

//        MAIN_SCENE.getChildren().setAll(root);
    }
    @FXML
    void chrome(ActionEvent event)throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Browser.class.getResource("browser_home_page.fxml")));

        root.setLayoutY(25);
        show_fxml(root);
//        MAIN_SCENE.getChildren().setAll(root);

    }
    @FXML
    void gallery(ActionEvent event)throws Exception {
        System.out.println("remaining");
    }
    @FXML
    void calculator(ActionEvent event)throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(Calculator.class.getResource("Calculator.fxml")));

        root.setLayoutY(25);
        show_fxml(root);
    }
    @FXML
    void payment_app(ActionEvent event)throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(upi.class.getResource("login_screen.fxml")));
        root.setLayoutY(25);
        show_fxml(root);
    }
    @FXML
    void clock(ActionEvent event)throws Exception{
        System.out.println("remaining");
    }



//helper methods
    void print_sys_msg(String msg){
        System.out.println("["+new get_sys_info().get_date_time_now()+"] : "+msg);
    }

}