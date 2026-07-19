package data.data.recent_apps;

import data.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class recent_apps extends Application {
    @FXML
    Button clear_button;
    @FXML
    ListView<ImageView> recent_app_list;
    Stack<Parent> temp = new Stack<>();
    List<ImageView> img_temp  = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {





        Parent root = FXMLLoader.load(getClass().getResource("recent_screen.fxml"));
//        MAIN_SCENE.getChildren().setAll(root);
        stage.setScene(new Scene(root));
        stage.show();

        }

    static void main(String[] args) {}
    @FXML
    public void initialize() throws IOException {
        make_thumbnail();
        recent_app_list.setFixedCellSize(220);
        recent_app_list.setItems(FXCollections.observableArrayList(img_temp));
        recent_app_list.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;"
        );

//        ObservableList<Parent> li = (ObservableList<Parent>) Main.recent_apps;
        ObservableList<ImageView> li = FXCollections.observableArrayList(img_temp);
       //A.I.
        recent_app_list.setCellFactory(list -> new ListCell<ImageView>() {
            private final ImageView view = new ImageView();
            @Override
            protected void updateItem(ImageView image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    setGraphic(image);
                    setAlignment(Pos.CENTER);
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 1) {
//                            System.out.println("Clicked app: " + getIndex());
//                            Main.MAIN_SCENE.getChildren().setAll(Main.recent_apps_stack.get(Main.recent_apps_stack.size()-getIndex()));
//                            System.out.println(Main.recent_apps_stack.get(Main.recent_apps_stack.size()-1-getIndex()));

                            int iindx = Main.recent_apps_stack.size()-1- getIndex();

                            Parent selected_app_screen = Main.recent_apps_stack.get(iindx);
                            Main.recent_apps_stack.remove(iindx);
                            Main.recent_apps_stack.push(selected_app_screen);
                            make_thumbnail();
                            selected_app_screen.setLayoutY(25);
                            Main.MAIN_SCENE.getChildren().setAll(selected_app_screen);
                        }
                    });
                    setStyle("-fx-background-color: null;");
                }
            }
        });
        recent_app_list.setItems(li);
    }

    @FXML
    public void clear_recent_apps() throws IOException {
//        Main.recent_apps.clear();
        temp.clear();
        img_temp.clear();;
        initialize();
    }

    void make_thumbnail(){

//       temp = Main.recent_apps_stack;
//        Collections.reverse(temp);
        temp = new Stack<>();
        temp.addAll(Main.recent_apps_stack);
        Collections.reverse(temp);
        for (Parent r : temp) {

            // Give the node a size before taking a snapshot
            Scene scene = new Scene(new StackPane(r));
            r.applyCss();
            r.autosize();
            r.layout();
            WritableImage snapshot = r.snapshot(new SnapshotParameters(), null);
            ImageView thumbnail = new ImageView(snapshot);
            thumbnail.setFitWidth(100);
            thumbnail.setFitHeight(200);
            thumbnail.setPreserveRatio(true);
            thumbnail.setSmooth(true);
            img_temp.add(thumbnail);
        }
    }
}
