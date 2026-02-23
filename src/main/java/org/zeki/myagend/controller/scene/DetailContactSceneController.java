package org.zeki.myagend.controller.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.zeki.myagend.controller.contact.ContactController;
import org.zeki.myagend.controller.contact.ContactFileController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DetailContactSceneController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSurname;

    @FXML
    private ImageView imgViewContactPhoto;

    private String pathImage;

    private ContactsSceneController parentController;

    @FXML
    public void initialize() {
        checkToCloseStage();
    }

    @FXML
    public void btnAddPhoto(ActionEvent event) {
        //Open Stage to select photo's path
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar foto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg", "*.gif"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        //Show photo and get url to save later
        if (file != null) {
            pathImage = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString());
            imgViewContactPhoto.setImage(image);
            imgViewContactPhoto.getStyleClass().add("selected");
        }
    }

    @FXML
    public void btnAddContact() {
        ContactFileController fileController = new ContactFileController();
        //check required fields
        if (checkEmptyFields()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Debe rellenar como mínimo: Nombre, Apellidos y Teléfono");
            alert.show();
            return;
        }
        //save contact data
        Map<String, String> contactData = new HashMap<>();
        contactData.put("name", txtName.getText());
        contactData.put("surname", txtSurname.getText());
        contactData.put("phone", txtPhone.getText());
        if (!txtEmail.toString().isBlank()) {
            contactData.put("email", txtEmail.getText());
        }
        if (pathImage != null) {
            contactData.put("photo", pathImage);
        }
        ContactController.getInstance().addNewContact(contactData);
        closeStage(txtName);
        fileController.saveContactsToFile();
        parentController.loadContactBox();
    }

    @FXML
    public void btcCancelNewContact(ActionEvent event) {
        //Check if it has completed any field to throw a warning message on cancel botton
        Node nodeEvent = (Node) event.getSource();
        Stage currentStage = (Stage) nodeEvent.getScene().getWindow();
        if (!checkTextFieldsWritten()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar ventana");
            alert.setHeaderText("¿Quieres cerrar la ventana sin agregar el contacto?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                currentStage.close();
            }
        } else {
            currentStage.close();
        }
    }

    private void checkToCloseStage() {
        //Check if it has completed any field to throw a warning message on exit botton
        Platform.runLater(() -> {       //Wait to load full Stage
            Stage currentStage = (Stage) txtName.getScene().getWindow();
            currentStage.setOnCloseRequest(event -> {
                event.consume();
                if (!checkTextFieldsWritten()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Cerrar ventana");
                    alert.setHeaderText("¿Quieres cerrar la ventana sin agregar el contacto?");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        currentStage.close();
                    }
                } else {
                    currentStage.close();
                }
            });
        });

    }

    private boolean checkTextFieldsWritten() {
        if (!txtName.getText().isBlank() || !txtSurname.getText().isBlank() ||
                !txtPhone.getText().isBlank() || !txtEmail.getText().isBlank() ||
                imgViewContactPhoto.getStyleClass().contains("selected")) {
            return false;
        }
        return true;
    }

    private boolean checkEmptyFields() {
        if (txtName.getText().isBlank() || txtSurname.getText().isBlank() ||
                txtPhone.getText().isBlank()) {
            return true;
        }
        return false;
    }

    private void closeStage(Node node) {
        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.close();
    }

    public void setParentController(ContactsSceneController parentController) {
        this.parentController = parentController;
    }
}
