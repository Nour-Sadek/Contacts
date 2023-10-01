package contacts;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Contact {
    private String firstName;
    private String surName;
    private String number;

    private static final Set<Contact> contacts = new LinkedHashSet<Contact>();

    private static final Pattern pattern1;
    private static final Pattern pattern2;
    //private static Pattern pattern3;

    static {
        // First group is wrapped with parentheses
        pattern1 = Pattern.compile("\\+?\\([^\\W_]+\\)([- ][^\\W_]{2,})*");
        // Second group or no groups are wrapped with parentheses
        pattern2 = Pattern.compile("\\+?[^\\W_]+([- ]\\([^\\W_]{2,}\\))*([- ][^\\W_]{2,})*");
    }

    public Contact(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
        this.number = "";

        Contact.contacts.add(this);
    }

    // Setters and getters methods

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setNumber(String number) {
        if (correctPhoneNumberFormat(number)) this.number = number;
        else {
            System.out.println("Wrong number format!");
            this.number = "";
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSurName() {
        return this.surName;
    }

    public String getNumber() {
        return this.number;
    }

    // Phone number specific methods

    private boolean hasNumber() {
        return !this.number.isEmpty();
    }

    private static boolean correctPhoneNumberFormat(String number) {
        Matcher matcher1 = Contact.pattern1.matcher(number);
        Matcher matcher2 = Contact.pattern2.matcher(number);

        return matcher1.matches() || matcher2.matches();
    }

    // Count, edit, remove, list operations

    static int count() {
        return Contact.contacts.size();
    }

    static void edit(int record, String field, String valueOfField) {
        int recordIndexInContacts = record - 1;

        int i = 0;
        Iterator<Contact> iter = Contact.contacts.iterator();

        while (iter.hasNext()) {
            Contact contact = iter.next();
            if (i == recordIndexInContacts) {
                switch (field) {
                    case "name":
                        contact.setFirstName(valueOfField);
                        break;
                    case "surname":
                        contact.setSurName(valueOfField);
                        break;
                    case "number":
                        contact.setNumber(valueOfField);
                        break;
                }
                break;
            }
            i++;
        }
    }

    static void remove(int record) {
        int recordIndexInContacts = record - 1;

        int i = 0;
        Iterator<Contact> iter = Contact.contacts.iterator();

        while (iter.hasNext()) {
            Contact contact = iter.next();
            if (i == recordIndexInContacts) {
                iter.remove();
                break;
            }
            i++;
        }
    }

    static void list() {

        int i = 1;
        Iterator<Contact> iter = Contact.contacts.iterator();

        while (iter.hasNext()) {
            Contact contact = iter.next();
            System.out.println(i + ". " + contact.displayContact());
            i++;
        }
    }

    private String displayContact() {
        String output = this.firstName + " " + this.surName + ", ";

        if (this.hasNumber()) {
            output += this.number;
        } else {
            output += "[no number]";
        }

        return output;
    }

}

