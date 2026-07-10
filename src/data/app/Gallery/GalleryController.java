package data.app.Gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.nio.file.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox mainBox;

    @FXML
    private VBox col1;

    @FXML
    private VBox col2;

    @FXML
    private VBox col3;

    private final String PHOTO_FOLDER =
            System.getProperty("user.home") + "\\Pictures\\Camera";

    private List<File> imageList = new ArrayList<>();

    @FXML
    public void initialize() {

        syncFolder();

//        FolderWatcher watcher =
//                new FolderWatcher(PHOTO_FOLDER, this::syncFolder);
//
//        watcher.start();
    }

    private void syncFolder() {

        File folder = new File(PHOTO_FOLDER);

        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        imageList.clear();

        for (File file : files) {

            String name = file.getName().toLowerCase();

            if (file.isFile()) {

                if (name.endsWith(".jpg")
                        || name.endsWith(".jpeg")
                        || name.endsWith(".png")) {

                    imageList.add(file);

                }

            }

        }

        Platform.runLater(() -> {

            refreshGallery();

        });

    }

    private void refreshGallery() {

        col1.getChildren().clear();

        col2.getChildren().clear();

        col3.getChildren().clear();

        Collections.reverse(imageList);

        loadImages();

    }
    private void loadImages() {

        int column = 0;

        for (File file : imageList) {

            //Image image = new Image(file.toURI().toString());
            Image image = new Image(
                    file.toURI().toString(),
                    200,
                    200,
                    true,
                    true
            );

            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(85);

            imageView.setPreserveRatio(true);

            StackPane card = new StackPane();

            card.getChildren().add(imageView);

            card.getStyleClass().add("image-card");

            // Left Click
            card.setOnMouseClicked(event -> {

                if (event.getButton() == MouseButton.PRIMARY) {

                    openImage(file);

                }

            });

            // Right Click Menu
            ContextMenu menu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");

            delete.setOnAction(e -> {

                deleteImage(file);

            });

            menu.getItems().add(delete);

            card.setOnContextMenuRequested(e -> {

                menu.show(card, e.getScreenX(), e.getScreenY());

            });

            if (column == 0) {

                col1.getChildren().add(card);

            } else if (column == 1) {

                col2.getChildren().add(card);

            } else {

                col3.getChildren().add(card);

            }

            column++;

            if (column == 3) {

                column = 0;

            }

        }

    }

    private void openImage(File file) {

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        Image image = new Image(file.toURI().toString());

        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);

        imageView.setFitWidth(300);

        ScrollPane scroll = new ScrollPane(imageView);

        scroll.setFitToWidth(true);

        scroll.setFitToHeight(true);

        Button zoomIn = new Button("+");

        Button zoomOut = new Button("-");

        zoomIn.setOnAction(e -> {

            imageView.setFitWidth(imageView.getFitWidth() + 50);

        });

        zoomOut.setOnAction(e -> {

            if (imageView.getFitWidth() > 100) {

                imageView.setFitWidth(imageView.getFitWidth() - 50);

            }

        });

        HBox buttons = new HBox(10);

        buttons.getChildren().addAll(zoomOut, zoomIn);

        buttons.setAlignment(Pos.CENTER);

        buttons.setPadding(new Insets(10));

        BorderPane root = new BorderPane();

        root.setCenter(scroll);

        root.setBottom(buttons);

        Scene scene = new Scene(root,300,600);

        stage.setTitle(file.getName());

        stage.setScene(scene);

        stage.show();

    }

    private void deleteImage(File file) {

        try {

            Files.deleteIfExists(file.toPath());

            syncFolder();

        }

        catch (Exception e) {

            System.out.println("Image delete thai nathi.");

        }

    }

}
