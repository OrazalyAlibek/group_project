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
                System.out.println("3. View All Rooms (with Categories)");
                System.out.println("4. Filter Rooms by Category (VIP, Standard, Economy)");
                System.out.println("5. Filter Rooms by Price");
                System.out.println("6. Book a Room");
                System.out.println("7. My Roommates");

                if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                    System.out.println("---------- ADMIN PANEL ----------");
                    System.out.println("8. Add New Room");
                    System.out.println("9. Remove User from Room (Evict)");
                }

                System.out.println("10. Logout");
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
                } else {
                    switch (option) {
                        case 3: System.out.println(roomController.getRoomsWithCategoryDetails()); break;
                        case 4: filterByCategoryMenu(); break;
                        case 5: filterByPriceMenu(); break;
                        case 6: bookRoomMenu(); break;
                        case 7:
                            if(currentUser.getRoomId() > 0) System.out.println(roomController.getRoommates(currentUser.getRoomId()));
                            else System.out.println("You do not have a room.");
                            break;
                        case 8:
                            if(isAdmin()) addRoomMenu();
                            else System.out.println("Access Denied");
                            break;
                        case 9:
                            if(isAdmin()) evictUserMenu();
                            else System.out.println("Access Denied");
                            break;
                        case 10:
                            currentUser = null;
                            System.out.println("Logged out.");
                            break;
                        case 0: return;
                        default: System.out.println("Invalid option");
                    }
                }
            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private boolean isAdmin() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
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

    private void filterByCategoryMenu() {
        System.out.print("Enter Category (Standard, VIP, Economy): ");
        String cat = scanner.nextLine();
        System.out.println(roomController.filterRoomsByCategory(cat));
    }

    private void filterByPriceMenu() {
        System.out.print("Enter max price: ");
        double max = scanner.nextDouble();
        System.out.println(roomController.getAvailableRoomsByPrice(max));
    }

    private void bookRoomMenu() {
        System.out.println(roomController.getRoomsWithCategoryDetails());
        System.out.print("Enter Room ID to book: ");
        int rid = scanner.nextInt();
        String result = roomController.bookRoom(currentUser.getId(), rid);
        System.out.println(result);

        User refreshed = userController.login(currentUser.getEmail(), currentUser.getPassword());
        if(refreshed != null) currentUser = refreshed;
    }

    private void addRoomMenu() {
        System.out.print("Room Num: "); int num = scanner.nextInt();
        System.out.print("Capacity: "); int cap = scanner.nextInt();
        System.out.print("Price: "); double pr = scanner.nextDouble();
        System.out.print("Cat ID (1=Std,2=VIP,3=Eco): "); int cid = scanner.nextInt();
        System.out.println(roomController.createRoom(num, cap, pr, cid));
    }

    private void evictUserMenu() {
        System.out.print("Enter User ID to remove from room: ");
        int uid = scanner.nextInt();
        System.out.println(roomController.evictUser(uid));
    }
}