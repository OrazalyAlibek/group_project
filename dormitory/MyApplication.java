
        package dormitory;

import dormitory.controllers.interfaces.IRoomController;
import dormitory.controllers.interfaces.IUserController;
import dormitory.models.User;
import java.util.Scanner;

public class MyApplication {
    private final IUserController userController;
    private final IRoomController roomController;
    private final Scanner scanner;
    private User currentUser = null;

    public MyApplication(IUserController userController, IRoomController roomController) {
        this.userController = userController;
        this.roomController = roomController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n--- DORMITORY SYSTEM ---");
            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
            } else {
                System.out.println("Logged in as: " + currentUser.getName());
                System.out.println("3. View All Rooms");
                System.out.println("4. Book a Room");
                System.out.println("5. Check Roommates");
                System.out.println("6. Logout");
            }
            System.out.println("0. Exit");
            System.out.print("Select: ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (currentUser == null) {
                    if (option == 1) registerMenu();
                    else if (option == 2) loginMenu();
                    else if (option == 0) break;
                    else System.out.println("Invalid option.");
                } else {
                    switch (option) {
                        case 3:
                            System.out.println(roomController.getAllRooms());
                            break;
                        case 4:
                            bookRoomMenu();
                            break;
                        case 5:
                            checkRoommatesMenu();
                            break;
                        case 6:
                            currentUser = null;
                            System.out.println("Logged out.");
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Invalid option.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Input error. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void registerMenu() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Surname: "); String surname = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Password: "); String password = scanner.nextLine();
        System.out.print("Gender: "); String gender = scanner.nextLine();

        System.out.println(userController.register(name, surname, email, password, gender));
    }

    private void loginMenu() {
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Password: "); String password = scanner.nextLine();

        User user = userController.login(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Welcome back!");
        } else {
            System.out.println("Login failed. Check credentials.");
        }
    }

    private void bookRoomMenu() {
        System.out.println(roomController.getAllRooms());
        System.out.print("Enter Room ID to book: ");
        int roomId = scanner.nextInt();

        System.out.println(roomController.bookRoom(currentUser.getId(), roomId));
    }


    private void checkRoommatesMenu() {
        System.out.print("Enter Room ID to check: ");
        int roomId = scanner.nextInt();

        System.out.println(roomController.getRoommates(roomId));
    }
}