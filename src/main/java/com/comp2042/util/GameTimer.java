package com.comp2042.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Game timer utility class, encapsulating the creation and control of Timeline
 */
public class GameTimer {
    private Timeline timeline;
    private Runnable onTick;

    /**
     * Constructor
     * @param intervalMillis interval in milliseconds
     * @param onTick callback function to be called on each tick
     */
    public GameTimer(long intervalMillis, Runnable onTick) {
        this.onTick = onTick;
        timeline = new Timeline(new KeyFrame(
                Duration.millis(intervalMillis),
                ae -> this.onTick.run()
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Start the timer
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stop the timer
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Pause the timer
     */
    public void pause() {
        timeline.pause();
    }

    /**
     * Resume the timer
     */
    public void play() {
        timeline.play();
    }

    /**
     * Set the interval of the timer
     * @param intervalMillis interval in milliseconds
     */
    public void setInterval(long intervalMillis) {
        timeline.stop();
        timeline.getKeyFrames().setAll(new KeyFrame(
                Duration.millis(intervalMillis),
                ae -> this.onTick.run()
        ));
        timeline.play();
    }
}