import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        scanner.nextLine(); // Clear buffer
        System.out.println("Enter patient details: ");
        System.out.println();
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Card No. (8 digit): ");
        String cardNo = scanner.nextLine();

        System.out.print("Disease: ");
        String disease = scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        try {
            String query = "INSERT INTO patients (name, age, gender, card_no, department, problem_desc) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, cardNo);
            preparedStatement.setString(5, department);
            preparedStatement.setString(6, disease);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added successfully");
            } else {
                System.out.println("Failed to add patient");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Patients:");
            System.out.println(
                    "+------------+-----------------------+--------+----------+--------------+-----------------+-------------------------------+");
            System.out.println(
                    "| Patient ID | Name                  | Age    | Gender   | Card No.     | Department      | Disease                       |");
            System.out.println(
                    "+------------+-----------------------+--------+----------+--------------+-----------------+-------------------------------+");

            while (resultSet.next()) {
                int patientId = resultSet.getInt("patient_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String cardNo = resultSet.getString("card_no");
                String department = resultSet.getString("department");
                String disease = resultSet.getString("problem_desc");

                System.out.printf("| %-10d | %-22s | %-6d | %-8s | %-12s | %-15s | %-29s |\n",
                        patientId, name, age, gender, cardNo, department, disease);
                System.out.println(
                        "+------------+-----------------------+--------+----------+--------------+-----------------+-------------------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int patientId) {
        String query = "SELECT * FROM patients WHERE patient_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}