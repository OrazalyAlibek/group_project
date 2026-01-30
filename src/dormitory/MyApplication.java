    package dormitory;

import dormitory.controllers.interfaces.IRoomController;
import dormitory.controllers.interfaces.IUserController;
import dormitory.models.User;
import java.util.Scanner;

public class MyApplication {
    private final IUserController userController;
    private final IRoomController roomController;
    private final Scanner scanner;
    private User currentUser;

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
                System.out.println("0. Exit");
            } else {
                System.out.println("Logged in as: " + currentUser.getName() + " [" + currentUser.getRole() + "]");
                System.out.println("3. View All Rooms (Detailed Report)");
                System.out.println("4. Filter Rooms by Price");
                System.out.println("5. Book a Room");
                System.out.println("6. Check Roommates");
                if (currentUser.getRole().equalsIgnoreCase("ADMIN")) {
                    System.out.println("7. [ADMIN] Add New Room");
                }
                System.out.println("8. Logout");
                System.out.println("0. Exit");
            }
            System.out.print("Select: ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                if (currentUser == null) {
                    if (option == 1) registerMenu();
                    else if (option == 2) loginMenu();
                    else if (option == 0) break;
                    else System.out.println("Invalid option");
                } else {
                    switch (option) {
                        case 3:
                            System.out.println(roomController.getRoomsWithCategoryDetails());
                            break;
                        case 4:
                            filterRoomsMenu();
                            break;
                        case 5:
                            bookRoomMenu();
                            break;
                        case 6:
                            checkRoommatesMenu();
                            break;
                        case 7:
                            if (currentUser.getRole().equalsIgnoreCase("ADMIN")) {
                                addRoomMenu();
                            } else {
                                System.out.println("Access Denied.");
                            }
                            break;
                        case 8:
                            currentUser = null;
                            System.out.println("Logged out.");
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Invalid option");
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
            System.out.println("Welcome " + user.getName());
        } else {
            System.out.println("Login failed.");
        }
    }

    private void filterRoomsMenu() {
        System.out.print("Enter max price: ");
        double maxPrice = scanner.nextDouble();
        System.out.println(roomController.getAvailableRoomsByPrice(maxPrice));
    }

    private void bookRoomMenu() {
        System.out.println(roomController.getAllRooms());
        System.out.print("Enter Room ID: ");
        int roomId = scanner.nextInt();
        System.out.println(roomController.bookRoom(currentUser.getId(), roomId));
    }

    private void checkRoommatesMenu() {
        System.out.print("Enter Room ID: ");
        int roomId = scanner.nextInt();
        System.out.println(roomController.getRoommates(roomId));
    }

    private void addRoomMenu() {
        System.out.print("Room Number: "); int num = scanner.nextInt();
        System.out.print("Capacity: "); int cap = scanner.nextInt();
        System.out.print("Price: "); double price = scanner.nextDouble();
        System.out.print("Category ID (1=Std, 2=VIP, 3=Eco): "); int catId = scanner.nextInt();
        System.out.println(roomController.createRoom(num, cap, price, catId));
    }
}