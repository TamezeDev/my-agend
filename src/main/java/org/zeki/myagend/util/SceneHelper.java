package org.zeki.myagend.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.zeki.myagend.controller.scene.ContactsSceneController;
import org.zeki.myagend.controller.scene.DetailContactSceneController;
import org.zeki.myagend.controller.scene.ThemeSceneController;
import org.zeki.myagend.controller.theme.ThemeFileController;
import org.zeki.myagend.model.Contact;

import java.io.IOException;
import java.util.Objects;

public class SceneHelper {

    public static void goToNewScene(Node node, String pathFxml) {
        try {
            //global function to load new scene
            FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(pathFxml));
            Parent root = loader.load();

            //load saved theme
            ThemeFileController themeController = new ThemeFileController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(SceneHelper.class.getResource(Path.getInstance().getGLOBAL_STYLES())).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(SceneHelper.class.getResource(themeController.loadConfigTheme())).toExternalForm());

            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al capturar algún atributo de nodo");
            e.getMessage();
        }
    }

    public static void goToNewStage(Node node, String pathUrl, ContactsSceneController reference, Contact contact) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(pathUrl));
            Parent root = loader.load();
            //load saved theme
            ThemeFileController themeController = new ThemeFileController();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(SceneHelper.class.getResource(Path.getInstance().getGLOBAL_STYLES())).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(SceneHelper.class.getResource(themeController.loadConfigTheme())).toExternalForm());
            Image mainIcon = new Image(Objects.requireNonNull(SceneHelper.class.getResourceAsStream(Path.getInstance().getMAIN_ICON())));
            Scene oldScene = node.getScene();
            Stage oldStage = (Stage) oldScene.getWindow();

            Stage newStage = new Stage();
            newStage.setWidth(500);
            newStage.setHeight(500);
            newStage.setResizable(false);
            newStage.getIcons().add(mainIcon);

            double[] showPosition;
            if (pathUrl.equals(Path.getInstance().getADD_CONTACT_VIEW())) {
                //Get reference to return contacts data on oldStage
                //Always newStage load centered
                DetailContactSceneController detailContactSceneController = loader.getController();
                detailContactSceneController.setParentController(reference);
                detailContactSceneController.setCurrentContact(contact);
                showPosition = setNewStagePosition(oldStage, newStage);
            } else {
                //Get reference to return contacts data on oldStage
                //load at right-up corner
                ThemeSceneController themeSceneController = loader.getController();
                themeSceneController.setSceneReference(oldScene);
                showPosition = setNewColorStage(oldStage, newStage);
            }
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

    private static double[] setNewColorStage(Stage oldStage, Stage newStage) {
        double xOffSet;
        double yOffSet;

        xOffSet = oldStage.getX() + (oldStage.getWidth() - (newStage.getWidth()));
        yOffSet = oldStage.getY();

        return new double[]{xOffSet, yOffSet};
    }
}
