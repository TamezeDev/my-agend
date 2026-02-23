package org.zeki.myagend.controller.contact;

import org.zeki.myagend.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactController {

    private static ContactController instance;
    private List<Contact> contacts;

    private ContactController() {
        contacts = new ArrayList<>();
    }

    public static ContactController getInstance() {
        if (instance == null) {
            instance = new ContactController();
        }
        return instance;
    }

    public void addNewContact(Map<String, String> contactData) {
        Contact contact = new Contact(contactData.get("name"), contactData.get("surname"), contactData.get("phone"));
        contact.setEmail(contactData.get("email"));
        contact.setPathPhoto(contactData.get("photo"));
        contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void loadUserContacts(){
        ContactFileController fileController = new ContactFileController();
        contacts = fileController.loadContactsFile();
    }
}



