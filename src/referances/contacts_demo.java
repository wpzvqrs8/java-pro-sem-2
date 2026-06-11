package referances;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class contacts_demo extends Application {

    @Override
    public void start(Stage stage) {

        // Root AnchorPane
        AnchorPane root = new AnchorPane();

        // Data source
        ObservableList<String> contacts = FXCollections.observableArrayList(
                "John Doe",
                "Jane Smith",
                "Mike Johnson",
                "Sarah Wilson"
        );

        // ListView
        ListView<String> contactList = new ListView<>(contacts);

        // Input field
        TextField contactField = new TextField();
        contactField.setPromptText("Enter contact name");

        // Add button
        Button addButton = new Button("Add Contact");

        // Add contact action
        addButton.setOnAction(e -> {
            String name = contactField.getText().trim();

            if (!name.isEmpty()) {
                contacts.add(name);
                contactField.clear();
                contactList.scrollTo(contacts.size() - 1);
            }
        });

        // Positioning with AnchorPane
        AnchorPane.setTopAnchor(contactField, 10.0);
        AnchorPane.setLeftAnchor(contactField, 10.0);
        AnchorPane.setRightAnchor(contactField, 100.0);

        AnchorPane.setTopAnchor(addButton, 10.0);
        AnchorPane.setRightAnchor(addButton, 10.0);

        AnchorPane.setTopAnchor(contactList, 50.0);
        AnchorPane.setLeftAnchor(contactList, 10.0);
        AnchorPane.setRightAnchor(contactList, 10.0);
        AnchorPane.setBottomAnchor(contactList, 10.0);

        root.getChildren().addAll(contactField, addButton, contactList);

        Scene scene = new Scene(root, 300, 600);

        stage.setTitle("Contacts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*

// ================== SUBCLASSES (Personal & Business Contacts)
// ==================
class PersonalContact extends Contact {
    String nickname;
    String birthday;

    // Constructor
    public PersonalContact(String name, String phoneNumber, String email, String category, String nickname,
                           String birthday) {
        super(name, phoneNumber, email, category);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    // Override displayDetails
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Nickname: " + nickname);
        System.out.println("Birthday: " + birthday);
    }
}

class BusinessContact extends Contact {
    String companyName;
    String jobTitle;

    // Constructor
    public BusinessContact(String name, String phoneNumber, String email, String category, String companyName,
                           String jobTitle) {
        super(name, phoneNumber, email, category);
        this.companyName = companyName;
        this.jobTitle = jobTitle;
    }

    // Override displayDetails
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Company: " + companyName);
        System.out.println("Job Title: " + jobTitle);
    }
}

// ================== CONTACT MANAGEMENT SYSTEM ==================
class ContactManagementSystem {
    //welocome
    {
        System.out.println( "╔════════════════════════════════════════════════════════════════════════╗" );
        System.out.println( "                ██╗           ██╗  ██╗ ███████╗ ████████╗                            " );
        System.out.println( "                ██║           ██║  ██║ ██╔════╝ ╚══██╔══╝                            " );
        System.out.println( "                ██║           ██║  ██║ █████╗      ██║                               " );
        System.out.println( "                ██║     ██║   ██║  ██║ ██╔══╝      ██║                               " );
        System.out.println( "                ███████╗ ███████║  ██║ ███████╗    ██║                               " );
        System.out.println( "                ╚══════╝ ╚══════╝  ╚═╝ ╚══════╝    ╚═╝                               " );
        System.out.println( "              L.J. INSTITUTE OF ENGINEERING AND TECHNOLOGY                     " );
        System.out.println( "                      CONTACT MANAGEMENT SYSTEM                           " );
        //System.out.println( "                      Ahmedabad                                 " );
        System.out.println( "╚════════════════════════════════════════════════════════════════════════╝" );
    }
    //
    Contact[] contacts;
    int contactCount;
    Scanner scanner = new Scanner(System.in);

    // Constructor
    public ContactManagementSystem(int maxContacts) {
        contacts = new Contact[maxContacts];
        contactCount = 0;

        // Predefined contacts
        contacts[contactCount++] = new PersonalContact(
                "Alice", "9876543210", "alice@gmail.com", "Friend", "Ally", "12/05/2000");

        contacts[contactCount++] = new BusinessContact(
                "Bob", "9123456780", "bob@company.com", "Work", "TechCorp", "Manager");

        contacts[contactCount++] = new PersonalContact(
                "Charlie", "9988776655", "charlie@yahoo.com", "Family", "Char", "25/12/1998");

        contacts[contactCount++] = new BusinessContact(
                "David", "9012345678", "david@biz.com", "Work", "BizSolutions", "Developer");

        contacts[contactCount++] = new PersonalContact(
                "tailor", "9090909090", "swift@mail.com", "Friend", "Evie", "08/08/2001");
    }

    // ================== VALIDATION METHODS (NO REGEX) ==================

    // Validate name (letters and spaces only)
    private boolean isValidName(String name) {
        if (name == null || name.length() < 2) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    // Validate phone number (digits only, 10 digits)
    private boolean isValidPhone(String phone) {
        if (phone == null || phone.length() != 10) {
            return false;
        }
        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Validate email (basic format check without regex)
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');
        return atIndex > 0 && dotIndex > atIndex && dotIndex < email.length() - 1;
    }

    // Validate date format (DD/MM/YYYY)
    private boolean isValidDate(String date) {
        if (date == null || date.length() != 10 || date.charAt(2) != '/' || date.charAt(5) != '/') {
            return false;
        }
        try {
            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));

            if (day < 1 || day > 31)
                return false;
            if (month < 1 || month > 12)
                return false;
            if (year < 1900 || year > 2100)
                return false;

            // Check for invalid dates (30/02, 31/04, 31/06, etc.)
            // Days in each month
            int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

            // Check for leap year
            boolean isLeapYear = false;
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) {
                        isLeapYear = true;
                    }
                } else {
                    isLeapYear = true;
                }
            }

            // Adjust February days for leap year
            if (isLeapYear) {
                daysInMonth[1] = 29;
            }

            // Validate day is within the month's allowed days
            if (day > daysInMonth[month - 1]) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if contact already exists
    private boolean isDuplicate(String name) {
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // ================== CORE FUNCTIONS WITH VALIDATION ==================

    // 1. Add a new contact with validation
    public void addContact() {
        if (contactCount >= contacts.length) {
            System.out.println("⚠️ Contact list is full!");
            return;
        }

        System.out.println(" === Add New Contact ===");

        // Contact Type Validation
        String type;
        while (true) {

            System.out.println("1. Personal Contact");
            System.out.println("2. Business Contact");
            System.out.print("Choose contact type (1 or 2): ");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                type = "Personal";
                break;
            } else if (input.equals("2")) {
                type = "Business";
                break;
            } else {
                System.out.println("⚠️ Invalid option! Please choose 1 or 2.");
            }
        }

        // Name Validation
        String name;
        while (true) {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            if (isValidName(name)) {
                if (!isDuplicate(name)) {
                    break;
                }
                System.out.println("⚠️ Contact with this name already exists!");
            } else {
                System.out.println("⚠️ Invalid name! Use letters only (min 2 characters).");
            }
        }

        // Phone Validation
        String phone;
        while (true) {
            System.out.print("Phone: ");
            phone = scanner.nextLine().trim();
            if (isValidPhone(phone)) {
                break;
            }
            System.out.println("⚠️ Invalid phone! Use 10 digits only.");
        }

        // Email Validation
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim().toLowerCase();
            if (isValidEmail(email)) {
                break;
            }
            System.out.println("⚠️ Invalid email format! Example: user@example.com");
        }

        // Category Validation
        String category;
        while (true) {
            System.out.print("Category (Family/Friend/Work/Other): ");
            category = scanner.nextLine().trim();

            // Check for digits in category
            boolean hasDigit = false;
            for (char c : category.toCharArray()) {
                if (Character.isDigit(c)) {
                    hasDigit = true;
                    break;
                }
            }

            if (hasDigit) {
                System.out.println("⚠️ Category cannot contain digits!");
                continue;
            }

            if (category.equalsIgnoreCase("Family") ||
                    category.equalsIgnoreCase("Friend") ||
                    category.equalsIgnoreCase("Work") ||
                    category.equalsIgnoreCase("Other")) {
                break;
            }
            System.out.println("⚠️ Invalid category! Choose from Family/Friend/Work/Other.");
        }

        if (type.equalsIgnoreCase("Personal")) {
            // Nickname Validation
            String nickname;
            while (true) {
                System.out.print("Nickname: ");
                nickname = scanner.nextLine().trim();
                if (isValidName(nickname)) {
                    break;
                }
                System.out.println("⚠️ Invalid nickname! Use letters only.");
            }

            // Birthday Validation
            String birthday;
            while (true) {
                System.out.print("Birthday (DD/MM/YYYY): ");
                birthday = scanner.nextLine().trim();
                if (isValidDate(birthday)) {
                    break;
                }
                System.out.println("⚠️ Invalid date format! Use DD/MM/YYYY.");
            }

            contacts[contactCount++] = new PersonalContact(name, phone, email, category, nickname, birthday);
        } else {
            // Company Validation
            String company;
            while (true) {
                System.out.print("Company: ");
                company = scanner.nextLine().trim();
                if (!company.isEmpty()) {
                    break;
                }
                System.out.println("⚠️ Company name cannot be empty!");
            }

            // Job Title Validation
            String jobTitle;
            while (true) {
                System.out.print("Job Title: ");
                jobTitle = scanner.nextLine().trim();
                if (!jobTitle.isEmpty()) {
                    break;
                }
                System.out.println("⚠️ Job title cannot be empty!");
            }

            contacts[contactCount++] = new BusinessContact(name, phone, email, category, company, jobTitle);
        }
        System.out.println("✅ Contact added successfully!");
    }

    // 2. Display all contacts
    public void displayAllContacts() {
        if (contactCount == 0) {
            System.out.println("⚠️ No contacts found!");
            return;
        }

        System.out.println(" === All Contacts (" + contactCount + ") ===");
        for (int i = 0; i < contactCount; i++) {
            contacts[i].displayDetails();
            System.out.println("-----------------------------");
        }
    }

    // 3. Search contact by name
    public void searchContact() {
        System.out.print(" Enter name to search: ");
        String searchName = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].name.equalsIgnoreCase(searchName)) {
                contacts[i].displayDetails();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("⚠️ Contact not found!");
        }
    }

    // 4. Update contact
    public void updateContact() {
        System.out.print(" Enter name to update: ");
        String oldName = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].name.equalsIgnoreCase(oldName)) {
                // Name Validation
                String newName;
                while (true) {
                    System.out.print("New name: ");
                    newName = scanner.nextLine().trim();
                    if (isValidName(newName)) {
                        // Check for duplicate, but allow the current contact's name
                        boolean isDup = false;
                        for (int j = 0; j < contactCount; j++) {
                            if (j != i && contacts[j].name.equalsIgnoreCase(newName)) {
                                isDup = true;
                                break;
                            }
                        }
                        if (!isDup) {
                            break;
                        }
                        System.out.println("⚠️ Contact with this name already exists!");
                    } else {
                        System.out.println("⚠️ Invalid name! Use letters only (min 2 characters).");
                    }
                }

                // Phone Validation
                String newPhone;
                while (true) {
                    System.out.print("New phone: ");
                    newPhone = scanner.nextLine().trim();
                    if (isValidPhone(newPhone)) {
                        break;
                    }
                    System.out.println("⚠️ Invalid phone! Use 10 digits only.");
                }

                // Email Validation
                String newEmail;
                while (true) {
                    System.out.print("New email: ");
                    newEmail = scanner.nextLine().trim().toLowerCase();
                    if (isValidEmail(newEmail)) {
                        break;
                    }
                    System.out.println("⚠️ Invalid email format! Example: user@example.com");
                }

                // Category Validation
                String newCategory;
                while (true) {
                    System.out.print("New category (Family/Friend/Work/Other): ");
                    newCategory = scanner.nextLine().trim();

                    // Check for digits in category
                    boolean hasDigit = false;
                    for (char c : newCategory.toCharArray()) {
                        if (Character.isDigit(c)) {
                            hasDigit = true;
                            break;
                        }
                    }

                    if (hasDigit) {
                        System.out.println("⚠️ Category cannot contain digits!");
                        continue;
                    }

                    if (newCategory.equalsIgnoreCase("Family") ||
                            newCategory.equalsIgnoreCase("Friend") ||
                            newCategory.equalsIgnoreCase("Work") ||
                            newCategory.equalsIgnoreCase("Other")) {
                        break;
                    }
                    System.out.println("⚠️ Invalid category! Choose from Family/Friend/Work/Other.");
                }

                contacts[i].updateContact(newName, newPhone, newEmail, newCategory);
                System.out.println("✅ Contact updated successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("⚠️ Contact not found!");
        }
    }

    // 5. Delete contact
    public void deleteContact() {
        System.out.print(" Enter name to delete: ");
        String deleteName = scanner.nextLine();

        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].name.equalsIgnoreCase(deleteName)) {
                // Shift remaining contacts
                for (int j = i; j < contactCount - 1; j++) {
                    contacts[j] = contacts[j + 1];
                }
                contactCount--;
                System.out.println("✅ Contact deleted successfully!");
                return;
            }
        }
        System.out.println("⚠️ Contact not found!");
    }

    // 6. Sort contacts by name (Bubble Sort)
    public void sortContacts() {
        for (int i = 0; i < contactCount - 1; i++) {
            for (int j = 0; j < contactCount - i - 1; j++) {
                if (contacts[j].name.compareToIgnoreCase(contacts[j + 1].name) > 0) {
                    Contact temp = contacts[j];
                    contacts[j] = contacts[j + 1];
                    contacts[j + 1] = temp;
                }
            }
        }
        System.out.println("✅ Contacts sorted by name!");
    }

    // 7. Filter contacts by category
    public void filterByCategory() {
        String category;
        while (true) {
            System.out.print(" Enter category to filter (Family/Friend/Work/Other): ");
            category = scanner.nextLine().trim();

            // Check for digits in category
            boolean hasDigit = false;
            for (char c : category.toCharArray()) {
                if (Character.isDigit(c)) {
                    hasDigit = true;
                    break;
                }
            }

            if (hasDigit) {
                System.out.println("⚠️ Category cannot contain digits!");
                continue;
            }

            if (category.equalsIgnoreCase("Family") ||
                    category.equalsIgnoreCase("Friend") ||
                    category.equalsIgnoreCase("Work") ||
                    category.equalsIgnoreCase("Other")) {
                break;
            }
            System.out.println("⚠️ Invalid category! Choose from Family/Friend/Work/Other.");
        }

        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].category.equalsIgnoreCase(category)) {
                contacts[i].displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("⚠️ No contacts in this category!");
        }
    }

    // 8. Count total contacts
    public void countContacts() {
        System.out.println(" Total contacts: " + contactCount);
    }

    // 9. Block Contact
    public void blockContact() {
        System.out.print(" Enter name to block: ");
        String blockName = scanner.nextLine();

        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].name.equalsIgnoreCase(blockName)) {
                // Here we can implement blocking logic, for now just a message
                System.out.println("✅ Contact " + blockName + " has been blocked!");
                return;
            }
        }
        System.out.println("⚠️ Contact not found!");
    }

    // ================== MAIN MENU ==================
    public void showMenu() {
        while (true) {
            System.out.println("📞 Contact Management System 📞");
            System.out.println("1. Add Contact");
            System.out.println("2. Display All Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Sort Contacts");
            System.out.println("7. Filter by Category");
            System.out.println("8. Count Contacts");
            System.out.println("9. Add Block Contact");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addContact();
                        break;
                    case 2:
                        displayAllContacts();
                        break;
                    case 3:
                        searchContact();
                        break;
                    case 4:
                        updateContact();
                        break;
                    case 5:
                        deleteContact();
                        break;
                    case 6:
                        sortContacts();
                        displayAllContacts();
                        break;
                    case 7:
                        filterByCategory();
                        break;
                    case 8:
                        countContacts();
                        break;
                    case 9:
                        blockContact();
                        break;

                    // return;
                    case 10:
                        System.out.println("👋 Exiting... Goodbye!");
                    default:
                        System.out.println("⚠️ Invalid option! Please choose 1-10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a number (1-10)!");
            }
        }
    }

    // ================== MAIN METHOD ==================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxContacts = 0;

        // Validate max contacts input
        while (true) {
            try {
                System.out.print("Enter max number of contacts (more than 5): ");
                maxContacts = Integer.parseInt(scanner.nextLine());
                if (maxContacts > 5) {
                    break;
                }
                System.out.println("⚠️ Invalid! Please enter a number greater than 5.");
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input! Please enter a number.");
            }
        }

        ContactManagementSystem cms = new ContactManagementSystem(maxContacts);
        cms.showMenu();
    }
}
* */
