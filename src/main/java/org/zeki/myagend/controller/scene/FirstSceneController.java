package org.zeki.myagend.controller.scene;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.zeki.myagend.util.Path;
import org.zeki.myagend.util.SceneHelper;

import java.io.IOException;
import java.util.Objects;

public class FirstSceneController extends Application {
    @FXML
    private Label title;
    @FXML
    private ImageView logoImage;

    @Override
    public void start(Stage stage) throws IOException {
        initStage(stage);

    }

    @FXML
    public void initialize() {
        generateTransitions();
    }

    private void initStage(Stage stage) throws IOException {
        //create start-stage
        String nameAPP = "Mi agenda";
        stage.setTitle(nameAPP);
        stage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.getInstance().getMAIN_AGEND_VIEW()));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Path.getInstance().getGLOBAL_STYLES())).toExternalForm());
        Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Path.getInstance().getMAIN_ICON())));

        stage.getIcons().add(mainIcon);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private void generateTransitions() {
        //At begin all invisible
        title.setOpacity(0.0);
        logoImage.setOpacity(0.0);
        //Appear title at 0.5s
        FadeTransition fadeTitle = new FadeTransition(Duration.seconds(1), title);
        fadeTitle.setFromValue(0);
        fadeTitle.setToValue(1);
        //Wait 1 second
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        //Appear logoImage at 0.5s
        FadeTransition fadeLogo = new FadeTransition(Duration.seconds(0.5), logoImage);
        fadeLogo.setFromValue(0);
        fadeLogo.setToValue(1);
        //Wait 2 second to introduce a new scene
        PauseTransition pauseFinal = new PauseTransition(Duration.seconds(2));
        String pathScene = Path.getInstance().getMAIN_AGEND_VIEW();
        pauseFinal.setOnFinished(event ->
                Platform.runLater(() -> SceneHelper.goToNewScene(title, pathScene))
        );
        //Sequence..
        SequentialTransition sequence = new SequentialTransition(
                fadeTitle, pauseTransition, fadeLogo, pauseFinal
        );
        sequence.play();
    }
}
