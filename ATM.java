import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private Map<String, User> users;
    private String userId;
    private Scanner scanner;

    public ATM() {
        // Sample user data for demonstration purposes
        users = new HashMap<>();
        users.put("123456", new User("123456", "pass123", 1000));
        users.put("987654", new User("987654", "pass987", 500));
        scanner = new Scanner(System.in);
    }

    private class User {
        String userId;
        String password;
        double balance;
        StringBuilder transactionHistory;

        public User(String userId, String password, double balance) {
            this.userId = userId;
            this.password = password;
            this.balance = balance;
            transactionHistory = new StringBuilder();
        }
    }

    private void login() {
        while (true) {
            System.out.print("Enter your User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter your Password: ");
            String password = scanner.nextLine();

            if (users.containsKey(userId) && users.get(userId).password.equals(password)) {
                System.out.println("Login successful.");
                this.userId = userId;
                break;
            } else {
                System.out.println("Invalid User ID or Password. Try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nWelcome to the ATM Interface");
        System.out.println("1. View Transaction History");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Quit");
    }

    private void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        System.out.println(users.get(userId).transactionHistory.toString());
    }

    private void withdrawMoney() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        User currentUser = users.get(userId);

        if (amount <= currentUser.balance) {
            currentUser.balance -= amount;
            currentUser.transactionHistory.append("Withdraw: -$" + amount + "\n");
            System.out.printf("Withdraw successful. Current balance: $%.2f%n", currentUser.balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void depositMoney() {
        System.out.print("Enter the amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        User currentUser = users.get(userId);

        currentUser.balance += amount;
        currentUser.transactionHistory.append("Deposit: +$" + amount + "\n");
        System.out.printf("Deposit successful. Current balance: $%.2f%n", currentUser.balance);
    }

    private void transferMoney() {
        System.out.print("Enter the receiver's User ID: ");
        String receiverId = scanner.nextLine();
        System.out.print("Enter the amount to transfer: ");
        double amount = Double.parseDouble(scanner.nextLine());
        User currentUser = users.get(userId);

        if (users.containsKey(receiverId) && amount <= currentUser.balance) {
            currentUser.balance -= amount;
            User receiverUser = users.get(receiverId);
            receiverUser.balance += amount;

            currentUser.transactionHistory.append("Transfer to " + receiverId + ": -$" + amount + "\n");
            receiverUser.transactionHistory.append("Transfer from " + userId + ": +$" + amount + "\n");

            System.out.printf("Transfer successful. Current balance: $%.2f%n", currentUser.balance);
        } else {
            System.out.println("Invalid receiver User ID or insufficient balance.");
        }
    }

    public void run() {
        login();

        while (true) {
            displayMenu();

            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewTransactionHistory();
                    break;
                case "2":
                    withdrawMoney();
                    break;
                case "3":
                    depositMoney();
                    break;
                case "4":
                    transferMoney();
                    break;
                case "5":
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}
