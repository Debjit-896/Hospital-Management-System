import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Print existing doctor details
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println(
                    "+-------------------+-----------------------+--------------------+----------------------+-------------------+");
            System.out.println(
                    "| Registration No.  | Name                  | Specialization     | Qualification        | Contact No.       |");
            System.out.println(
                    "+-------------------+-----------------------+--------------------+----------------------+-------------------+");

            while (resultSet.next()) {
                int regNo = resultSet.getInt("reg_no");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String qualification = resultSet.getString("qualification");
                String contactNo = resultSet.getString("contact_no");

                System.out.printf("| %-17d | %-21s | %-18s | %-22s | %-15s |\n",
                        regNo, name, specialization, qualification, contactNo);
                System.out.println(
                        "+-------------------+-----------------------+--------------------+----------------------+-------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if doctor exists by reg_no
    public boolean getDoctorById(int regNo) {
        String query = "SELECT * FROM doctors WHERE reg_no = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, regNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}