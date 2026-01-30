package dormitory.models;

public class Room {
    private int id;
    private int roomNumber;
    private int capacity;
    private double price;
    private int categoryId;

    public Room(int roomNumber, int capacity, double price, int categoryId) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.categoryId = categoryId;
    }

    public void setId(int id) {
        this.id = id; 
    }
    
    public int getId() { 
        return id; 
    }
    
    public int getRoomNumber() {
        return roomNumber; 
    }
    
    public int getCapacity() {
        return capacity; 
    }
    
    public double getPrice() {
        return price; 
    }
    
    public int getCategoryId() { 
        return categoryId; 
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Room #" + roomNumber + " | Price: " + price;
    }
}
