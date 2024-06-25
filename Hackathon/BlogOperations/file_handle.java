import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class user_data implements Serializable{
    private String name;
    private String email;
    private String password;
    private long mobile_no;

    	

	// Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(long mobile_no) {
        this.mobile_no = mobile_no;
    }
}

public class file_handle extends BlogManager  {

    private static final String FILENAME = "users.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<user_data> users = loadUsersFromFile();

        int choice;
        do {
            System.out.println("1.Register\n2.Login\n3.Show all users\n0.Exit");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    registerUser(sc, users);
                    break;

                case 2:
                    loginUser(sc, users);
                    break;

                case 3:
                    showAllUsers(users);
                    break;

                case 4:
                    try {
                        saveUsersToFile(users);
                        System.out.println("Users data saved to file.");
                    } catch (IOException e) {
                        System.out.println("Failed to save users to file: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } while (choice != 0);

        sc.close();
    }

    private static void registerUser(Scanner sc, List<user_data> users) {
        System.out.println("Enter name:");
        String name = sc.next();
        System.out.println("Enter Email:");
        String email = sc.next();
        System.out.println("Enter Password:");
        String pass = sc.next();
        System.out.println("Enter Mobile_no:");
        long mobile_no = sc.nextLong();

        user_data newUser = new user_data();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(pass);
        newUser.setMobile_no(mobile_no);

        users.add(newUser);

        try {
            saveUsersToFile(users); // Save user data to file after registration
            System.out.println("User registered successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save user data to file: " + e.getMessage());
        }
    }

    private static void loginUser(Scanner sc, List<user_data> users) {
        System.out.println("Enter your Email:");
        String email = sc.next();
        System.out.println("Enter your Password:");
        String pass = sc.next();

        for (user_data user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(pass)) {
                BlogManager.blogmenu();
                System.out.println("Login successful! Welcome " + user.getName());
                
                return;
            }
        }
        System.out.println("Invalid credentials!");
    }

    private static void showAllUsers(List<user_data> users) {
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            System.out.println("Registered Users:");
            for (user_data user : users) {
                System.out.println("Name: " + user.getName() + ", Email: " + user.getEmail());
            }
        }
    }

    private static void saveUsersToFile(List<user_data> users) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            outputStream.writeObject(users);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<user_data> loadUsersFromFile() {
        List<user_data> users = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILENAME))) {
            Object obj = inputStream.readObject();
            if (obj instanceof List) {
                users = (List<user_data>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
           }
        return users;
    }
}
