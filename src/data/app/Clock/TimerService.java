package data.app.Clock;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class TimerService {

    public static int totalSeconds = 0;

    public static Timeline timeline;

    public static Runnable onTick;

    public static Runnable onFinish;

    public static void start(int seconds){

        totalSeconds = seconds;

        if(timeline != null)
            timeline.stop();

        timeline = new Timeline(

                new KeyFrame(Duration.seconds(1), e -> {

                    totalSeconds--;

                    if(onTick != null)
                        Platform.runLater(onTick);

                    if(totalSeconds <= 0){

                        timeline.stop();

                        if(onFinish != null)
                            Platform.runLater(onFinish);

                    }

                })

        );

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

    }

    public static void stop(){

        if(timeline != null)
            timeline.stop();

    }

}