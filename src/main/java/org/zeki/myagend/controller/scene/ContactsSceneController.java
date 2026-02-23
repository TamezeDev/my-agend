package org.zeki.myagend.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.zeki.myagend.controller.contact.ContactController;
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
    public void initialize() {
        ContactController.getInstance().loadUserContacts();
        loadContactBox();
    }

    @FXML
    private void addContact(ActionEvent event) {
        Node nodeEvent = (Node) event.getSource();
        String addContactPath = Path.getInstance().getADD_CONTACT_VIEW();
        SceneHelper.goToNewStage(nodeEvent, addContactPath, this);
    }

    public void loadContactBox() {
        boxPaneContactList.getChildren().clear();
        List<Contact> contacts = ContactController.getInstance().getContacts();
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
        return fullContactContainer;
    }

    private VBox createFullNameBox(Contact contact) {
        Label fullName = new Label(contact.getName() + " " + contact.getSurname());
        HBox optionBox = createOptionBox();
        VBox nameContainer = new VBox(fullName, optionBox);
        nameContainer.setSpacing(20);
        nameContainer.setAlignment(Pos.CENTER);
        nameContainer.setPrefWidth(340);
        return nameContainer;
    }

    private HBox createOptionBox() {
        //options
        ImageView callImg = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getCALL_ICON()))));
        ImageView editImg = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getEDIT_ICON()))));
        ImageView deleteImg = new ImageView(new Image(Objects.requireNonNull(Path.class.getResourceAsStream(Path.getInstance().getDELETE_ICON()))));
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

        return optionHbox;
    }
}
