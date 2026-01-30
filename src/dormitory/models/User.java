package dormitory.models;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String gender;
    private String role;
    private int roomId;

    public User(String name, String surname, String email, String password, String gender, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() { 
        return name; 
    }
    public String getSurname() { 
        return surname; 
    }
    
    public String getEmail() { 
        return email;
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public String getGender() { 
        return gender; 
    }
    
    public String getRole() { 
        return role; 
    }
    
    public int getRoomId() {
        return roomId; 
    }
    
    public void setRoomId(int roomId) { 
        this.roomId = roomId; 
    }

    @Override
    public String toString() {
        return id + ". " + name + " " + surname + " (" + role + ")";
    }
}
