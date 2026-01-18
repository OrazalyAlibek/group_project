package dormitory.models.Room;

public class Room {

    private int id;
    private int roomNumber;
    private int capacity;
    private double price;

    public Room(int roomNumber, int capacity, double price) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
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

    @Override
    public String toString() {
        return "ID: " + id + " | Room #" + roomNumber + " | Cap: " + capacity + " | Price: " + price;
    }
    
}
