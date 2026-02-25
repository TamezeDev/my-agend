package org.zeki.myagend.controller.contact;

import org.zeki.myagend.model.Contact;

import java.util.ArrayList;
import java.util.Comparator;
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

        return contacts.stream().sorted(Comparator.comparing(Contact::getName)).toList();
    }

    public List<Contact> filterByLetters(String letter) {
        return contacts.stream().filter(contact -> contact.getName().toLowerCase().contains(letter)
                        || contact.getName().toUpperCase().contains(letter)
                        || contact.getSurname().toLowerCase().contains(letter)
                        || contact.getSurname().toUpperCase().contains(letter))
                .sorted(Comparator.comparing(Contact::getName)).toList();
    }

    public void loadUserContacts() {
        ContactFileController fileController = new ContactFileController();
        contacts = fileController.loadContactsFile();
    }

    public void deleteContact(String fullName) {
        contacts.removeIf(contact -> {
            String nameToCheck = contact.getName() + " " + contact.getSurname();
            return fullName.equals(nameToCheck);
        });
    }

    public Contact getSingleContact(String fullName) {
        return contacts.stream().filter(contact -> (contact.getName() + " " + contact.getSurname()).equals(fullName)).findFirst().orElse(null);
    }

    public boolean checkContactInList(Contact contact) {
        return contacts.contains(contact);
    }

    public boolean checkIfExistsSameName(String fullName) {
        return contacts.stream().anyMatch(contact -> (contact.getName() + " " + contact.getSurname()).equals(fullName));
    }
}



