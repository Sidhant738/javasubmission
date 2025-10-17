import java.sql.*;
import java.util.Scanner;

public class StudentManagementApp {

    // ✅ H2 Database Connection (in-memory)
    static final String URL = "jdbc:h2:mem:studentdb;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASS = "";

    // Establish connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Create table if not exists
    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS student (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50),
                age INT,
                course VARCHAR(50)
            );
        """;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add student
    public static void addStudent(String name, int age, String course) {
        String sql = "INSERT INTO student (name, age, course) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.executeUpdate();
            System.out.println("✅ Student Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all students
    public static void viewStudents() {
        String sql = "SELECT * FROM student";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\nID | Name | Age | Course");
            System.out.println("----------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | "
                        + rs.getString("name") + " | "
                        + rs.getInt("age") + " | "
                        + rs.getString("course"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update student
    public static void updateStudent(int id, String newCourse) {
        String sql = "UPDATE student SET course = ? WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newCourse);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student Updated Successfully!");
            else
                System.out.println("⚠️ No Student Found with ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete student
    public static void deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student Deleted Successfully!");
            else
                System.out.println("⚠️ No Student Found with ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main menu
    public static void main(String[] args) {
        try {
            // ✅ Load H2 JDBC Driver
            Class.forName("org.h2.Driver");
            createTable();

            Scanner sc = new Scanner(System.in);
            int choice;

            System.out.println("🎓 Welcome to Student Management System (H2 Database)");

            while (true) {
                System.out.println("\n1️⃣ Add Student");
                System.out.println("2️⃣ View Students");
                System.out.println("3️⃣ Update Student");
                System.out.println("4️⃣ Delete Student");
                System.out.println("5️⃣ Exit");
                System.out.print("👉 Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Name: ");
                        String name = sc.next();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        System.out.print("Enter Course: ");
                        String course = sc.next();
                        addStudent(name, age, course);
                    }
                    case 2 -> viewStudents();
                    case 3 -> {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        System.out.print("Enter New Course: ");
                        String course = sc.next();
                        updateStudent(id, course);
                    }
                    case 4 -> {
                        System.out.print("Enter Student ID to Delete: ");
                        int id = sc.nextInt();
                        deleteStudent(id);
                    }
                    case 5 -> {
                        System.out.println("👋 Exiting... Goodbye!");
                        sc.close();
                        System.exit(0);
                    }
                    default -> System.out.println("❌ Invalid choice! Try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

