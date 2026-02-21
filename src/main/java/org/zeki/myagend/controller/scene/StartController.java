package org.zeki.myagend.controller.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.zeki.myagend.util.Path;

import java.io.IOException;
import java.util.Objects;

public class StartController extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        initStage(stage);
    }

    private void initStage(Stage stage) throws IOException {
        //create start-stage
        String nameAPP = "Mi agenda";
        stage.setTitle(nameAPP);
        stage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.getInstance().getSTART_VIEW()));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Path.getInstance().getGLOBAL_STYLES())).toExternalForm());
        Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Path.getInstance().getMAIN_ICON())));

        stage.getIcons().add(mainIcon);
        stage.setMinWidth(800);
        stage.setMaxHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}
