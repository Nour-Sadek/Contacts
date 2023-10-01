package contacts;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter action (add, remove, edit, count, list, exit):");
            String userInput = scanner.next();

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
                case "list":
                    listRecords();
                    break;
            }
        }
    }

    private static void addRecord() {

        System.out.println("Enter the name:");
        String firstName = scanner.next();
        System.out.println("Enter the surname:");
        String surName = scanner.next();
        System.out.println("Enter the number:");
        scanner.nextLine();
        String number = scanner.nextLine();

        Contact contact = new Contact(firstName, surName);
        contact.setNumber(number);

        System.out.println("The recorded added.");
    }

    private static void removeRecord() {
        if (Contact.count() == 0) {
            System.out.println("No records to remove!");
            return;
        }

        Contact.list();

        System.out.println("Select a record:");
        int record = Integer.parseInt(scanner.next());
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
        int record = Integer.parseInt(scanner.next());
        System.out.println("Select a field (name, surname, number):");
        String field = scanner.next();
        System.out.println("Enter " + field + ":");
        scanner.nextLine();
        String valueOfField = scanner.nextLine();
        Contact.edit(record, field, valueOfField);

        System.out.println("The record updated!");
    }

    private static void countRecords() {
        System.out.println("The Phone Book has " + Contact.count() + " record(s).");
    }

    private static void listRecords() {
        Contact.list();
    }
}

