import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/FINAL";
    private static final String USER = "postgres";
    private static final String PASSWORD = "masha65bu";

    public void saveStudent(Student student) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO students (name, course, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setString(2, student.getCourse().getName());
            statement.setString(3, student.getEmail());

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Студент успешно зарегистрирован.");
            }

            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM students";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String courseName = resultSet.getString("course");
                String email = resultSet.getString("email");

                Course course;
                if (courseName.equals("Programming Course")) {
                    course = new ProgrammingCourse();
                } else {
                    course = new DesignCourse();
                }

                Student student = new RegularStudent(name, course, email);
                students.add(student);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return students;
    }
}
