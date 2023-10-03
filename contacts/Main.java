package contacts;

import java.util.Scanner;
import java.io.*;
import java.util.Set;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final ContactStaticFactory contactStaticFactory = new ContactStaticFactory();

    public static void main(String[] args) {
        String fileName = "phonebook.db";

        try {
            Set<Contact> contacts = (Set<Contact>) SerializationUtils.deserialize(fileName);
            Contact.setContacts(contacts);
            System.out.println("open " + fileName + "\n");
        } catch (IOException | ClassNotFoundException e) {
            String message = e.getMessage();
            System.out.println(message);
        }

        while (true) {
            System.out.println("Enter action (add, remove, edit, count, info, exit):");
            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) break;

            switch (userInput) {
                case "add":
                    addRecord();
                    break;
                case "remove":
                    removeRecord();
                    break;
                case "edit":
                    editRecord();
                    break;
                case "count":
                    countRecords();
                    break;
                case "info":
                    infoOfRecords();
                    break;
            }

            System.out.println();
        }

        try {
            SerializationUtils.serialize(Contact.getContacts(), fileName);
        } catch (IOException e) {
            String message = e.getMessage();
            System.out.println(message);
        }
    }

    private static void addRecord() {

        System.out.println("Enter the type (person, organization):");
        Contact.Type type = findByName(scanner.nextLine());
        Contact contact;

        if (type == Contact.Type.PERSON) {
            System.out.println("Enter the name:");
            String name = scanner.nextLine();
            contact = contactStaticFactory.newInstance(type, name);
            PersonContact personContact = (PersonContact) contact;

            System.out.println("Enter the surname:");
            String surName = scanner.nextLine();
            personContact.setSurName(surName);

            System.out.println("Enter the birth date:");
            String birthDate = scanner.nextLine();
            personContact.setBirthDate(birthDate);

            System.out.println("Enter the gender:");
            String gender = scanner.nextLine();
            personContact.setGender(gender);

            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            personContact.setNumber(number);

        } else {
            System.out.println("Enter the organization name:");
            String name = scanner.nextLine();
            contact = contactStaticFactory.newInstance(type, name);
            OrganizationContact organizationContact = (OrganizationContact) contact;

            System.out.println("Enter the address:");
            String address = scanner.nextLine();
            organizationContact.setAddress(address);

            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            organizationContact.setNumber(number);
        }

        System.out.println("The recorded added.");
    }

    private static Contact.Type findByName(String name) {
        Contact.Type output = null;
        for (Contact.Type contactType: Contact.Type.values()) {
            if (contactType.name().equalsIgnoreCase(name)) {
                output = contactType;
                break;
            }
        }
        return output;
    }

    private static void removeRecord() {
        if (Contact.count() == 0) {
            System.out.println("No records to remove!");
            return;
        }

        Contact.list();

        System.out.println("Select a record:");
        int record = Integer.parseInt(scanner.nextLine());
        Contact.remove(record);

        System.out.println("The record removed!");
    }

    private static void editRecord() {
        if (Contact.count() == 0) {
            System.out.println("No records to edit!");
            return;
        }

        Contact.list();

        System.out.println("Select a record:");
        int record = Integer.parseInt(scanner.nextLine());
        Contact contact = Contact.findContact(record);
        if (contact.getClass() == PersonContact.class) {
            System.out.println("Select a field (name, surname, birth, gender, number):");
            String field = scanner.nextLine();
            System.out.println("Enter " + field + ": ");
            String valueOfField = scanner.nextLine();
            ((PersonContact) contact).edit(field, valueOfField);
        } else {
            System.out.println("Select a field (address, number):");
            String field = scanner.nextLine();
            System.out.println("Enter " + field + ": ");
            String valueOfField = scanner.nextLine();
            ((OrganizationContact) contact).edit(field, valueOfField);
        }

        System.out.println("The record updated!");
    }

    private static void countRecords() {
        System.out.println("The Phone Book has " + Contact.count() + " record(s).");
    }

    private static void infoOfRecords() {
        if (Contact.count() == 0) {
            System.out.println("No records to show info of!");
            return;
        }

        Contact.list();

        System.out.println("Enter index to show info:");
        int record = Integer.parseInt(scanner.nextLine());
        Contact contact = Contact.findContact(record);
        if (contact.getClass() == PersonContact.class) {
            ((PersonContact) contact).getInfo();
        } else {
            ((OrganizationContact) contact).getInfo();
        }
    }
}

