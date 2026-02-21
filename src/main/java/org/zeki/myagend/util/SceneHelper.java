package org.zeki.myagend.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneHelper {

    public static void goToNewScene(Node node, String pathFxml) {
        try {
            //global function to load new scene
            FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(pathFxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(SceneHelper.class.getResource(Path.getInstance().getGLOBAL_STYLES())).toExternalForm());

            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al capturar Stage del nodo");
            e.getMessage();
        }
    }
}
