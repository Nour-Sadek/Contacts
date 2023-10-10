# Contacts

### Learning Outcomes

- The essential basics of object-oriented programming: encapsulation, inheritance and polymorphism. 
- Applying regular expressions
- Serialization basics

### About

This program allows the user to create and save contacts (like we do on a phone), and search for organizations or people by name.
Only two categories of contacts are represented: person and organization, with each category having different fields to fill out.

For a person, the required fields are: name, surname, birthdate, gender, number.

For an organization, the required fields are: name, address, number

A number is considered to be of a correct format if it matches the following pattern:
1. The phone number should be split into groups using a space or dash. One group is also possible.
2. Before the first group, there may or may not be a plus symbol.
3. The first group or the second group can be wrapped in parentheses, but there should be no more than one group that is 
wrapped in parentheses. There may also be no groups wrapped in parentheses.
4. A group can contain numbers, uppercase, and lowercase English letters. A group should be at least 2 symbols in length. 
But the first group may be only one symbol in length.

NOTE: for now, the program doesn't check for correct user input, so exceptions for incorrect input are expected.

# General Info

To learn more about this project, please visit [HyperSkill Website - Contacts (Java)](https://hyperskill.org/projects/43).

This project's difficulty has been labelled as __Hard__ where this is how
HyperSkill describes each of its four available difficulty levels:

- __Easy Projects__ - if you're just starting
- __Medium Projects__ - to build upon the basics
- __Hard Projects__ - to practice all the basic concepts and learn new ones
- __Challenging Projects__ - to perfect your knowledge with challenging tasks

This repository contains

    contacts package
        - Contains the contacts.Main java class that contains the main method to run the project
        - Contains the contacts.Contact java class that is used to store the contacts provided by the user
        - Contains the contacts.SerializationUtils java class that allows for serialization of contacts information


Project was built using java version 8 update 381

# How to Run

Download the contacts repository to your machine. Create a new project in IntelliJ IDEA, then move the downloaded contacts 
repository to the src folder and run the contacts.Main java class.
