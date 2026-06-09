package referances;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class IPCameraViewer extends Application {

    @Override
    public void start(Stage stage) {

        WebView view = new WebView();

        String html = """
            <html>
            <body style="margin:0;background:black;overflow:hidden">
                <img src="http://192.168.29.252:8080"
                     style="width:100vw;height:100vh;object-fit:cover;">
            </body>
            </html>
            """;

        view.getEngine().loadContent(html);

        Scene scene = new Scene(view, 1000, 700);

        stage.setScene(scene);
        stage.setTitle("Camera");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}