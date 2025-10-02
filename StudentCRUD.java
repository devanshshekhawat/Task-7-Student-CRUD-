import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";      // replace with your MySQL username
    private static final String PASSWORD = "root";  // replace with your MySQL password

    private Connection conn;
    private Scanner sc;

    public StudentCRUD() {
        sc = new Scanner(System.in);
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add student
    public void addStudent() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.executeUpdate();
            System.out.println("🎉 Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all students
    public void viewStudents() {
        String sql = "SELECT * FROM students";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n📋 Student List:");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Age: %d | Course: %s%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("course"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update student
    public void updateStudent() {
        System.out.print("Enter Student ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter New Course: ");
        String course = sc.nextLine();

        String sql = "UPDATE students SET name=?, age=?, course=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student updated successfully!");
            else
                System.out.println("⚠️ Student not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete student
    public void deleteStudent() {
        System.out.print("Enter Student ID to Delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM students WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("🗑️ Student deleted successfully!");
            else
                System.out.println("⚠️ Student not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menu
    public void menu() {
        while (true) {
            System.out.println("\n========= STUDENT CRUD MENU =========");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> {
                    System.out.println("👋 Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    public static void main(String[] args) {
        StudentCRUD app = new StudentCRUD();
        app.menu();
    }
}

