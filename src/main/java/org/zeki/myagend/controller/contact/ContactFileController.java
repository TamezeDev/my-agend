package org.zeki.myagend.controller.contact;

import org.zeki.myagend.model.Contact;
import org.zeki.myagend.util.Path;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactFileController {

    public void saveContactsToFile() {
        File file = new File(Path.getInstance().getCONTACTS_FILE());
        List<Contact> contacts = ContactController.getInstance().getContacts();
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                contacts.forEach(contact -> {
                    try {
                        oos.writeObject(contact);
                    } catch (IOException e) {
                        System.err.println("Error al guardar datos en el fichero");
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo de contactos");
        }
    }

    public List<Contact> loadContactsFile() {
        File file = new File(Path.getInstance().getCONTACTS_FILE());
        List<Contact> contacts = new ArrayList<>();
        if (file.exists()){
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

                while (true) {
                    try {
                        Contact contact = (Contact) ois.readObject();
                        contacts.add(contact);
                    }catch (EOFException e){
                        //End reading file, break
                        break;
                    }
                    catch (ClassNotFoundException e) {
                        System.err.println("Error en la lectura en el fichero de contactos");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error de acceso a la ruta del fichero de contactos");
            }
        }
        return contacts;
    }

}
