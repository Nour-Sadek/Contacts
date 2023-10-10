package contacts;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Contact implements Serializable {
    protected String name;
    protected String number;
    protected LocalDateTime timeAdded;
    protected LocalDateTime timeEdited;
    protected static final LocalDate today;

    private static Set<Contact> contacts = new LinkedHashSet<>();

    private static final Pattern pattern1;
    private static final Pattern pattern2;


    static {
        // First group is wrapped with parentheses
        pattern1 = Pattern.compile("\\+?\\([^\\W_]+\\)([- ][^\\W_]{2,})*");
        // Second group or no groups are wrapped with parentheses
        pattern2 = Pattern.compile("\\+?[^\\W_]+([- ]\\([^\\W_]{2,}\\))*([- ][^\\W_]{2,})*");
        today = LocalDate.now();
    }

    enum Type {
        ORGANIZATION, PERSON
    }

    Contact(String name) {
        this.name = name;
        this.timeAdded = LocalDateTime.now();
        this.timeEdited = LocalDateTime.now();
        this.number = "";

        Contact.contacts.add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        if (correctPhoneNumberFormat(number)) this.number = number;
        else this.number = "";
    }

    protected boolean hasNumber() {
        return !this.number.isEmpty();
    }

    private static boolean correctPhoneNumberFormat(String number) {
        Matcher matcher1 = Contact.pattern1.matcher(number);
        Matcher matcher2 = Contact.pattern2.matcher(number);

        return matcher1.matches() || matcher2.matches();
    }

    static int count() {
        return Contact.contacts.size();
    }

    static void remove(Contact contact) {
        Contact.contacts.remove(contact);
        System.out.println("Contact deleted.\n");
    }

    static void list() {

        int i = 1;

        for (Contact contact : Contact.contacts) {
            System.out.println(i + ". " + contact);
            i++;
        }

        System.out.println();
    }

    static Contact findContact(int record) {
        int recordIndexInContacts = record - 1;

        int i = 0;

        for (Contact contact : Contact.contacts) {
            if (i == recordIndexInContacts) return contact;
            i++;
        }

        return null;
    }

    protected static void setContacts(Set<Contact> contacts) {
        Contact.contacts = contacts;
    }

    protected static Set<Contact> getContacts() {
        return Contact.contacts;
    }

    protected void getInfo() {}

    void editRecord() {}

}

class PersonContact extends Contact {

    private String surName;
    private LocalDate birthDate;
    private Gender gender;
    private static final Pattern birthDatePattern;

    static {
        birthDatePattern = Pattern.compile("([1-9]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])");
    }

    enum Gender {
        M, F, ENBY
    }

    PersonContact(String name) {
        super(name);
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setBirthDate(String birthDate) {
        if (correctBirthDateFormat(birthDate) && today.isAfter(LocalDate.parse(birthDate))) this.birthDate = LocalDate.parse(birthDate);
        else this.birthDate = null;
    }

    private boolean hasBirthDate() {
        return this.birthDate != null;
    }

    private static boolean correctBirthDateFormat(String birthDate) {
        Matcher matcher = PersonContact.birthDatePattern.matcher(birthDate);

        return matcher.matches();
    }

    public void setGender(String gender) { this.gender = findByName(gender); }

    private static Gender findByName(String name)  {
        Gender output = null;
        for (Gender gender: Gender.values()) {
            if (gender.name().equalsIgnoreCase(name)) {
                output = gender;
                break;
            }
        }
        return output;
    }

    private boolean hasGender() {
        return this.gender != null;
    }

    protected void getInfo() {
        String birthDate;
        if (this.hasBirthDate()) birthDate = this.birthDate.toString();
        else birthDate = "[no data]";

        String gender;
        if (this.hasGender()) gender = this.gender.toString();
        else gender = "[no data]";

        String number;
        if (this.hasNumber()) number = this.number;
        else number = "[no number]";

        System.out.println("Name: " + this.name + "\n" +
                "Surname: " + this.surName + "\n" +
                "Birth date: " + birthDate + "\n" +
                "Gender: " + gender + "\n" +
                "Number: " + number + "\n" +
                "Time created: " + this.timeAdded + "\n" +
                "Time last edit: " + this.timeEdited + "\n");
    }

    void editRecord() {
        System.out.println("Select a field (name, surname, birth, gender, number):");
        String field = Main.scanner.nextLine();
        System.out.println("Enter " + field + ": ");
        String valueOfField = Main.scanner.nextLine();
        this.edit(field, valueOfField);
        System.out.println("Saved");
        this.getInfo();
    }
    void edit(String field, String valueOfField) {
        switch (field) {
            case "name":
                this.setName(valueOfField);
                break;
            case "surname":
                this.setSurName(valueOfField);
                break;
            case "birth":
                this.setBirthDate(valueOfField);
                break;
            case "gender":
                this.setGender(valueOfField);
                break;
            case "number":
                this.setNumber(valueOfField);
                break;
        }
        this.timeEdited = LocalDateTime.now();
    }

    public String toString() {
        return this.name + " " + this.surName;
    }

}

class OrganizationContact extends Contact {
    private String address;

    OrganizationContact(String name) {
        super(name);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected void getInfo() {
        System.out.println("Organization name: " + this.name + "\n" +
                "Address: " + this.address);

        String number;
        if (this.hasNumber()) number = this.number;
        else number = "[no number]";

        System.out.println("Number: " + number + "\n" +
                "Time created: " + this.timeAdded + "\n" +
                "Time last edit: " + this.timeEdited + "\n");

    }

    void editRecord() {
        System.out.println("Select a field (name, address, number):");
        String field = Main.scanner.nextLine();
        System.out.println("Enter " + field + ": ");
        String valueOfField = Main.scanner.nextLine();
        this.edit(field, valueOfField);
        System.out.println("Saved");
        this.getInfo();
    }

    void edit(String field, String valueOfField) {
        switch (field) {
            case "address":
                this.setAddress(valueOfField);
                break;
            case "number":
                this.setNumber(valueOfField);
                break;
            case "name":
                this.setName(valueOfField);
                break;
        }
        this.timeEdited = LocalDateTime.now();
    }

    public String toString() {
        return this.name;
    }
}

class ContactStaticFactory {

    public Contact newInstance(Contact.Type type, String name) {
        switch (type) {
            case PERSON -> {
                return new PersonContact(name);
            }
            case ORGANIZATION -> {
                return new OrganizationContact(name);
            }
            default -> {
                return null;
            }
        }
    }

    public PersonContact setAttributes(Contact contact, String number, String surName, String birthDate, String gender) {
        PersonContact personContact = (PersonContact) contact;
        personContact.setSurName(surName);
        personContact.setBirthDate(birthDate);
        personContact.setGender(gender);
        personContact.setNumber(number);

        return personContact;
    }

    public OrganizationContact setAttributes(Contact contact, String number, String address) {
        OrganizationContact organizationContact = (OrganizationContact) contact;
        organizationContact.setAddress(address);
        organizationContact.setNumber(number);

        return organizationContact;
    }

}
