package org.zeki.myagend.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import org.zeki.myagend.util.Path;
import org.zeki.myagend.util.SceneHelper;

public class ContactsSceneController {

    @FXML
    public void initialize(){

    }

    @FXML
    public void addContact(ActionEvent event){
        Node nodeEvent = (Node)event.getSource();
        String addContactPath = Path.getInstance().getADD_CONTACT_VIEW();
        SceneHelper.goToNewStage(nodeEvent, addContactPath);
    }
}
