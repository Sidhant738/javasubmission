import java.util.*;
import java.util.stream.Collectors;

// Employee Class
class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", salary=" + salary + '}';
    }
}

// Student Class
class Student {
    int id;
    String name;
    int grade;

    Student(int id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + '\'' + ", grade=" + grade + '}';
    }
}

// Product Class
class Product {
    int id;
    String name;
    double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}

public class c {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ----- Part A: Employee Sorting -----
        System.out.println("Enter number of Employees:");
        int nEmp = sc.nextInt();
        sc.nextLine(); // consume newline
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < nEmp; i++) {
            System.out.println("Enter Employee ID, Name, Salary:");
            int id = sc.nextInt();
            sc.nextLine();
            String name = sc.nextLine();
            double salary = sc.nextDouble();
            sc.nextLine();
            employees.add(new Employee(id, name, salary));
        }

        // Sort by salary, then name
        employees.sort((e1, e2) -> {
            if (e1.salary != e2.salary)
                return Double.compare(e1.salary, e2.salary);
            return e1.name.compareTo(e2.name);
        });

        System.out.println("\nSorted Employees:");
        employees.forEach(System.out::println);

        // ----- Part B: Student Filtering & Sorting -----
        System.out.println("\nEnter number of Students:");
        int nStu = sc.nextInt();
        sc.nextLine();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < nStu; i++) {
            System.out.println("Enter Student ID, Name, Grade:");
            int id = sc.nextInt();
            sc.nextLine();
            String name = sc.nextLine();
            int grade = sc.nextInt();
            sc.nextLine();
            students.add(new Student(id, name, grade));
        }

        List<Student> filteredSortedStudents = students.stream()
                .filter(s -> s.grade >= 75)
                .sorted(Comparator.comparing(s -> s.name))
                .collect(Collectors.toList());

        System.out.println("\nFiltered and Sorted Students (Grade >= 75):");
        filteredSortedStudents.forEach(System.out::println);

        // ----- Part C: Product Stream Operations -----
        System.out.println("\nEnter number of Products:");
        int nProd = sc.nextInt();
        sc.nextLine();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < nProd; i++) {
            System.out.println("Enter Product ID, Name, Price:");
            int id = sc.nextInt();
            sc.nextLine();
            String name = sc.nextLine();
            double price = sc.nextDouble();
            sc.nextLine();
            products.add(new Product(id, name, price));
        }

        List<String> expensiveProducts = products.stream()
                .filter(p -> p.price > 500)
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .map(p -> p.name.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("\nProducts with Price > 500 (Descending, Names Uppercase):");
        expensiveProducts.forEach(System.out::println);

        sc.close();
    }
}