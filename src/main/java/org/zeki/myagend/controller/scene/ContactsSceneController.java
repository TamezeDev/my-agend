package org.zeki.myagend.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.zeki.myagend.controller.contact.ContactController;
import org.zeki.myagend.controller.contact.ContactFileController;
import org.zeki.myagend.model.Contact;
import org.zeki.myagend.util.Path;
import org.zeki.myagend.util.SceneHelper;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class ContactsSceneController {

    @FXML
    private VBox boxPaneContactList;
    @FXML
    private TextField txtSearch;

    private boolean ignoreClicks;

    @FXML
    public void initialize() {
        ContactController.getInstance().loadUserContacts();
        loadContactBox(ContactController.getInstance().getContacts());
        filterContact();
    }

    @FXML
    private void addContact(ActionEvent event) {
        Node nodeEvent = (Node) event.getSource();
        String addContactPath = Path.getInstance().getADD_CONTACT_VIEW();
        SceneHelper.goToNewStage(nodeEvent, addContactPath, this, null);
    }

    private void filterContact() {
        txtSearch.textProperty().addListener(((obs, oldText, newText) -> loadContactBox(ContactController.getInstance().filterByLetters(newText))));
    }

    public void loadContactBox(List<Contact> contacts) {
        ignoreClicks = true;
        boxPaneContactList.getChildren().clear();
        contacts.forEach(contact -> {
            HBox contactCard = createImgBox(contact);
            boxPaneContactList.getChildren().add(contactCard);
        });
    }

    private HBox createImgBox(Contact contact) {
        VBox nameContainer = createFullNameBox(contact);
        ImageView photoImgView;
        if (contact.getPathPhoto() != null) {
            photoImgView = new ImageView(new Image(new File(contact.getPathPhoto()).toURI().toString()));
        } else {
            photoImgView = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getEMPTY_PHOTO()))));
        }
        photoImgView.setPreserveRatio(true);
        photoImgView.setFitWidth(80);
        photoImgView.setFitHeight(80);
        HBox fullContactContainer = new HBox(photoImgView, nameContainer);
        fullContactContainer.setSpacing(30);
        fullContactContainer.setAlignment(Pos.CENTER);
        fullContactContainer.getStyleClass().add("contact");
        showContactInfo(fullContactContainer);
        return fullContactContainer;
    }

    private void removeVisibleContact(VBox root) {
        if (root == null) return;
        root.getChildren().removeIf(node -> node.getStyleClass().contains("info"));
    }

    private void showContactInfo(HBox fullContactContainer) {

        fullContactContainer.setOnMouseClicked(ev -> {
            if (ignoreClicks) {
                ignoreClicks = false;
                return;
            }
            HBox nodeSelected = (HBox) ev.getSource();
            VBox parent = (VBox) nodeSelected.getParent();
            if (parent == null) return;
            removeVisibleContact(parent);
            VBox boxLabel = (VBox) nodeSelected.getChildren().get(1);
            Label label = (Label) boxLabel.getChildren().getFirst();
            String fullName = label.getText();
            int indexSelected = parent.getChildren().indexOf(nodeSelected);

            VBox contactInfo = createContactInfoBox(fullName);
            parent.getChildren().add(indexSelected + 1, contactInfo);
        });
    }

    private VBox createFullNameBox(Contact contact) {
        Label fullName = new Label(contact.getName() + " " + contact.getSurname());
        HBox optionBox = createOptionBox(fullName.getText());
        VBox nameContainer = new VBox(fullName, optionBox);
        nameContainer.setSpacing(20);
        nameContainer.setAlignment(Pos.CENTER);
        nameContainer.setPrefWidth(340);
        return nameContainer;
    }

    private void deleteContactContainer(ImageView deleteImgView, String nameToDelete) {
        deleteImgView.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrado de contacto");
            alert.setHeaderText("¿Estas seguro que quieres eliminar el contacto?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                ContactFileController fileController = new ContactFileController();
                ContactController.getInstance().deleteContact(nameToDelete);
                loadContactBox(ContactController.getInstance().getContacts());
                fileController.saveContactsToFile();
            }
        });
    }

    private VBox createContactInfoBox(String nameToGetInfo) {

        Contact contact = ContactController.getInstance().getSingleContact(nameToGetInfo);

        Label fullName = new Label("Nombre: " + contact.getName() + " " + contact.getSurname());
        Label phone = new Label("Teléfono: " + contact.getPhone());
        Label email = new Label();
        if (!contact.getEmail().isBlank()) {
            email.setText("Email: " + contact.getEmail());
        } else {
            email.setText("Email: Desconocido");
        }
        VBox contactInfo = new VBox(fullName, phone, email);
        contactInfo.setAlignment(Pos.CENTER);
        contactInfo.setSpacing(15);

        contactInfo.getStyleClass().add("info");

        return contactInfo;
    }

    private HBox createOptionBox(String fullName) {
        //options
        ImageView callImg = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getCALL_ICON()))));
        ImageView editImg = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getEDIT_ICON()))));
        ImageView deleteImg = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getDELETE_ICON()))));
        callImg.getStyleClass().add("option");
        editImg.getStyleClass().add("option");
        deleteImg.getStyleClass().add("option");
        callImg.setFitHeight(30);
        callImg.setFitWidth(30);
        callImg.setPreserveRatio(true);
        editImg.setFitHeight(30);
        editImg.setFitWidth(30);
        editImg.setPreserveRatio(true);
        deleteImg.setFitHeight(30);
        deleteImg.setFitWidth(30);
        deleteImg.setPreserveRatio(true);
        HBox optionHbox = new HBox(callImg, editImg, deleteImg);
        optionHbox.setAlignment(Pos.CENTER);
        optionHbox.setSpacing(30);

        deleteContactContainer(deleteImg, fullName);
        editContact(editImg, fullName);
        showCallingInfo(callImg, fullName);

        return optionHbox;
    }

    private void showCallingInfo(ImageView callImg, String fullName) {
        callImg.setOnMouseClicked(event -> {
            ignoreClicks = true;
            Contact contact = ContactController.getInstance().getSingleContact(fullName);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Llamada");
            alert.setHeaderText("Llamando a " + contact.getPhone() + " - " + contact.getName() + " " + contact.getSurname());
            alert.setContentText("Hey espabila!! Esto es una simulación. ¿Como vas a llamar a un número de teléfono desde el ordenador? 😏");
            alert.show();
        });
    }

    public void editContact(ImageView editImg, String fullName) {
        editImg.setOnMouseClicked(event -> {
            ignoreClicks = true;
            Node node = (Node) event.getSource();
            Contact contact = ContactController.getInstance().getSingleContact(fullName);
            String pathFXML = Path.getInstance().getADD_CONTACT_VIEW();
            SceneHelper.goToNewStage(node, pathFXML, this, contact);
        });
    }
}
