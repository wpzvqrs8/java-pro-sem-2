package data.app.Gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryApp extends Application {

    private final String PHOTO_FOLDER = System.getProperty("user.home") + "\\Pictures\\Camera";
    private final List<File> images = new ArrayList<>();

    private VBox col1 = new VBox(10);
    private VBox col2 = new VBox(10);

    @Override
    public void start(Stage stage) {
        // 3-Column Masonry Layout setup
        HBox masonry = new HBox(0, col1, col2);
        masonry.setPadding(new Insets(15));
        masonry.setAlignment(Pos.TOP_CENTER);

        // Making columns responsive so they divide the width equally
        col1.prefWidthProperty().bind(masonry.widthProperty().subtract(10).divide(2));
        col2.prefWidthProperty().bind(masonry.widthProperty().subtract(10).divide(2));

        ScrollPane scroll = new ScrollPane(masonry);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("gallery-scroll");

        syncFolder();

        // Start background folder watcher
        FolderWatcher watcher = new FolderWatcher(PHOTO_FOLDER, this::syncFolder);
        watcher.start();

        Scene scene = new Scene(scroll, 300, 600);

        // Optional CSS styling
        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
            // App will run normally even if styles.css is missing
        }

        stage.setTitle("Simple Masonry Gallery");
        stage.setScene(scene);
        stage.show();
    }

    private void syncFolder() {
        File folder = new File(PHOTO_FOLDER);
        File[] files = folder.listFiles();

        if (files == null) return;

        images.clear();
        for (File file : files) {
            String name = file.getName().toLowerCase();
            if (file.isFile() && (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"))) {
                images.add(file);
            }
        }

        Platform.runLater(this::refreshGallery);
    }

    private void refreshGallery() {
        col1.getChildren().clear();
        col2.getChildren().clear();
        loadImages();
    }

    private void loadImages() {
        List<File> imageList = new ArrayList<>(images);
        Collections.reverse(imageList); // Show newest images first

        int index = 0;
        for (File file : imageList) {
            try {
                // Background loading to keep the app responsive
                Image image = new Image(file.toURI().toString(), 90, 0, true, true);
                ImageView iv = new ImageView(image);
                iv.setFitWidth(80);
                iv.setPreserveRatio(true);
                iv.getStyleClass().add("gallery-image");

                StackPane card = new StackPane(iv);
                card.getStyleClass().add("image-card");

                // Feature 1: Left Click -> Open Image Viewer
                card.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        openImageViewer(file);
                    }
                });

                // Feature 2: Right Click -> Delete Context Menu
                ContextMenu contextMenu = new ContextMenu();
                MenuItem deleteItem = new MenuItem("Delete Image");
                deleteItem.setOnAction(event -> deleteImage(file));
                contextMenu.getItems().add(deleteItem);

                card.setOnContextMenuRequested(e -> contextMenu.show(card, e.getScreenX(), e.getScreenY()));

                // Distribute items into 2 columns dynamically
                if (index % 2 == 0)
                    col1.getChildren().add(card);
                else
                    col2.getChildren().add(card);

                index++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Feature 3: Image Viewer Window with Zoom Controls
    private void openImageViewer(File file) {
        Stage viewerStage = new Stage();
        viewerStage.initModality(Modality.APPLICATION_MODAL); // Blocks main window until closed
        viewerStage.setTitle(file.getName());

        Image targetImg = new Image(file.toURI().toString());
        ImageView viewerIV = new ImageView(targetImg);
        viewerIV.setPreserveRatio(true);
        viewerIV.setFitWidth(250); // Default open width

        ScrollPane imgScroll = new ScrollPane(new StackPane(viewerIV));
        imgScroll.setFitToWidth(true);
        imgScroll.setFitToHeight(true);

        // Zoom Control Buttons
        Button btnZoomIn = new Button("Zoom +");
        Button btnZoomOut = new Button("Zoom -");

        btnZoomIn.setOnAction(e -> {
            viewerIV.setFitWidth(viewerIV.getFitWidth() * 1.2);
            viewerIV.setFitHeight(viewerIV.getFitHeight() * 1.2);
        });

        btnZoomOut.setOnAction(e -> {
            viewerIV.setFitWidth(viewerIV.getFitWidth() / 1.2);
            viewerIV.setFitHeight(viewerIV.getFitHeight() / 1.2);
        });

        HBox controls = new HBox(15, btnZoomOut, btnZoomIn);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(imgScroll);
        root.setBottom(controls);

        Scene viewerScene = new Scene(root, 300, 600);
        viewerStage.setScene(viewerScene);
        viewerStage.show();
    }

    // Right-click Delete Execution
    private void deleteImage(File file) {
        try {
            Files.deleteIfExists(file.toPath());
            syncFolder(); // Automatically refreshes UI after deletion
        } catch (Exception e) {
            System.out.println("Could not delete file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}