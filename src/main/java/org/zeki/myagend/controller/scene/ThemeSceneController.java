package org.zeki.myagend.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.zeki.myagend.controller.theme.ThemeFileController;
import org.zeki.myagend.util.Path;

import java.util.Objects;

public class ThemeSceneController {
    ThemeFileController fileController = new ThemeFileController();
    private Scene sceneReference;

    @FXML
    public void initialize() {
    }

    @FXML
    public void selectAutumnTheme(ActionEvent event) {
        Button nodeEvent = (Button) event.getSource();
        applyThemeSelected(nodeEvent, Path.getInstance().getAUTUMN_THEME_STYLE());
        fileController.setThemSelected(nodeEvent.getText());
    }

    @FXML
    public void selectDefaultTheme(ActionEvent event) {
        Button nodeEvent = (Button) event.getSource();
        applyThemeSelected(nodeEvent, Path.getInstance().getDEFAULT_THEME_STYLE());
        fileController.setThemSelected(nodeEvent.getText());
    }

    @FXML
    public void selectSpringTheme(ActionEvent event) {
        Button nodeEvent = (Button) event.getSource();
        applyThemeSelected(nodeEvent, Path.getInstance().getSPRING_THEME_STYLE());
        fileController.setThemSelected(nodeEvent.getText());
    }

    @FXML
    public void selectSummerTheme(ActionEvent event) {
        Button nodeEvent = (Button) event.getSource();
        applyThemeSelected(nodeEvent, Path.getInstance().getSUMMER_THEME_STYLE());
        fileController.setThemSelected(nodeEvent.getText());
    }

    @FXML
    public void selectWinterTheme(ActionEvent event) {
        Button nodeEvent = (Button) event.getSource();
        applyThemeSelected(nodeEvent, Path.getInstance().getWINTER_THEME_STYLE());
        fileController.setThemSelected(nodeEvent.getText());
    }

    @FXML
    public void acceptChanges(ActionEvent event) {

        Node node = (Node)event.getSource();
        Stage currentStage = (Stage) node.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Colores");
        alert.setHeaderText("Paleta de colores guardada correctamente");
        alert.show();
        fileController.saveConfigTheme();
        System.out.println("Guardado ok");
        currentStage.close();
    }

    private void applyThemeSelected(Button node, String pathTheme) {
        Scene scene = node.getScene();
        scene.getStylesheets().removeIf(sheet -> sheet.contains("default") || sheet.contains("spring") || sheet.contains("summer") || sheet.contains("autumn") || sheet.contains("winter"));
        sceneReference.getStylesheets().removeIf(sheet -> sheet.contains("default") || sheet.contains("spring") || sheet.contains("summer") || sheet.contains("autumn") || sheet.contains("winter"));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(pathTheme)).toExternalForm());
        sceneReference.getStylesheets().add(Objects.requireNonNull(getClass().getResource(pathTheme)).toExternalForm());
    }

    public void setSceneReference(Scene sceneReference) {
        this.sceneReference = sceneReference;
    }
}
