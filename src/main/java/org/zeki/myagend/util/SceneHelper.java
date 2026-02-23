package org.zeki.myagend.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.zeki.myagend.controller.scene.ContactsSceneController;
import org.zeki.myagend.controller.scene.DetailContactSceneController;

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
            System.err.println("Error al capturar algún atributo de nodo");
            e.getMessage();
        }
    }

    public static void goToNewStage(Node node, String pathUrl, ContactsSceneController reference) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(pathUrl));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(SceneHelper.class.getResource(Path.getInstance().getGLOBAL_STYLES())).toExternalForm());

            Stage oldStage = (Stage) node.getScene().getWindow();
            //Get reference to return contacts data on oldStage
            DetailContactSceneController detailContactSceneController = loader.getController();
            detailContactSceneController.setParentController(reference);

            Stage newStage = new Stage();
            newStage.setWidth(500);
            newStage.setHeight(500);
            newStage.setResizable(false);
            double[] showPosition = setNewStagePosition(oldStage, newStage); //Always newStage load centered
            newStage.setX(showPosition[0]);
            newStage.setY(showPosition[1]);

            newStage.setScene(scene);
            newStage.initOwner(oldStage);
            newStage.initModality(Modality.WINDOW_MODAL); //Block main stage until close new stage
            newStage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error al capturar algún atributo de nodo");
            e.getMessage();
        }

    }
    private static double[] setNewStagePosition(Stage oldStage, Stage newStage) {
        double xOffSet;
        double yOffSet;

        xOffSet = oldStage.getX() + (oldStage.getWidth() / 2 - (newStage.getWidth() / 2));
        yOffSet = oldStage.getY() + (oldStage.getHeight() / 2 - (newStage.getHeight() / 2) + 50);

        return new double[]{xOffSet, yOffSet};

    }
}
