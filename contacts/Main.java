package contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final ContactStaticFactory contactStaticFactory = new ContactStaticFactory();

    public static void main(String[] args) {
        String fileName = "phonebook.db";

        // deserialize Contacts from phonebook.db
        try {
            Set<Contact> contacts = (Set<Contact>) SerializationUtils.deserialize(fileName);
            Contact.setContacts(contacts);
            System.out.println("open " + fileName + "\n");
        } catch (IOException | ClassNotFoundException e) {
            String message = e.getMessage();
            System.out.println(message);
        }

        while (true) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) break;

            switch (userInput) {
                case "add":
                    addRecord();
                    break;
                case "search":
                    searchForRecords();
                    break;
                case "count":
                    countRecords();
                    break;
                case "list":
                    infoOfRecords();
                    break;
            }

            System.out.println();
        }

        // Serialize Contacts to phonebook.db
        try {
            SerializationUtils.serialize(Contact.getContacts(), fileName);
        } catch (IOException e) {
            String message = e.getMessage();
            System.out.println(message);
        }
    }

    private static void searchForRecords() {
        System.out.println("Enter search query:");
        String query = scanner.nextLine();

        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        Set<Contact> contacts = Contact.getContacts();
        List<Contact> matchedContacts = new ArrayList<>();

        for (Contact contact: contacts) {

            matcher = pattern.matcher(contact.toString());

            if (matcher.find()) {
                matchedContacts.add(contact);
            }

        }

        if (matchedContacts.isEmpty()) {
            System.out.println("Found 0 results\n");
        } else {
            if (matchedContacts.size() == 1) System.out.println("Found 1 result:");
            else System.out.println("Found " + matchedContacts.size() + " results:");

            int i = 1;
            for (Contact contact: matchedContacts) {
                System.out.println(i + ". " + contact);
                i++;
            }
            System.out.println();

            searchMenu(matchedContacts);
        }

    }

    private static void searchMenu(List<Contact> matchedContacts) {
        System.out.println("[search] Enter action ([number], back, again):");
        String userInput = scanner.nextLine();

        if (userInput.equals("again")) searchForRecords();
        else if (isInt(userInput)) {
            int number = Integer.parseInt(userInput);
            if (number > 0 && number <= matchedContacts.size()) {
                Contact requiredContact = matchedContacts.get(number - 1);
                requiredContact.getInfo();
                recordMenu(requiredContact);
            } else {
                System.out.println("Incorrect record requested.");
            }
        }
    }

    private static void recordMenu(Contact requiredContact) {
        System.out.println("[record] Enter action (edit, delete, menu):");
        String userInput = scanner.nextLine();

        switch (userInput) {
            case "menu":
                return;
            case "edit":
                requiredContact.editRecord();
                recordMenu(requiredContact);
                break;
            case "delete":
                Contact.remove(requiredContact);
                break;
        }

    }

    private static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
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

            System.out.println("Enter the surname:");
            String surName = scanner.nextLine();

            System.out.println("Enter the birth date:");
            String birthDate = scanner.nextLine();

            System.out.println("Enter the gender (M, F, ENBY):");
            String gender = scanner.nextLine();

            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            contactStaticFactory.setAttributes(contact, number, surName, birthDate, gender);


        } else {
            System.out.println("Enter the organization name:");
            String name = scanner.nextLine();
            contact = contactStaticFactory.newInstance(type, name);

            System.out.println("Enter the address:");
            String address = scanner.nextLine();

            System.out.println("Enter the number:");
            String number = scanner.nextLine();

            contactStaticFactory.setAttributes(contact, number, address);

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

    private static void countRecords() {
        System.out.println("The Phone Book has " + Contact.count() + " record(s).");
    }

    private static void infoOfRecords() {
        if (Contact.count() == 0) {
            System.out.println("No records to show info of!");
            return;
        }

        Contact.list();

        listMenu();

    }

    private static void listMenu() {
        System.out.println("[list] Enter action ([number], back):");
        String userInput = scanner.nextLine();


        if (isInt(userInput)) {
            int number = Integer.parseInt(userInput);
            if (number > 0 && number <= Contact.getContacts().size()) {
                Contact contact = Contact.findContact(number);
                assert contact != null;
                contact.getInfo();
                recordMenu(contact);
            }
        }

    }
}
