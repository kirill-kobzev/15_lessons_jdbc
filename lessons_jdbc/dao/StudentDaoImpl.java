package lessons_jdbc.dao;

import lessons_jdbc.connectionManager.ConnectionManager;
import lessons_jdbc.connectionManager.ConnectionManagerJdbcImpl;
import lessons_jdbc.pojo.City;
import lessons_jdbc.pojo.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao {
    private static ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    @Override
    public boolean addStudent(Student student) {
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO students VALUES (DEFAULT, ?, ?, ?, ?, ?)");
        ) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getFamilyName());
            statement.setInt(3, student.getAge());
            statement.setString(4, student.getContact());
            statement.setInt(5, student.getCity().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Student getStudentById(int id) {
        Connection connection = connectionManager.getConnection();
        Student student = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * from students LEFT JOIN city on students.id = city.id WHERE students.id = ?");
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = new Student(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("family_name"),
                            resultSet.getInt("age"),
                            resultSet.getString("contact"),
                            new City(resultSet.getInt("id"),
                                     resultSet.getString("name"),
                                     resultSet.getInt("citizens")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return student;
    }

    @Override
    public boolean update(Student student) {
        if (student.getId() != 0) {
            Connection connection = connectionManager.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE students SET name=?, family_name=?, age=?, contact=?, city=? WHERE id=?");
            ) {
                statement.setString(1, student.getName());
                statement.setString(2, student.getFamilyName());
                statement.setInt(3, student.getAge());
                statement.setString(4, student.getContact());
                statement.setInt(5, student.getCity().getId());
                statement.setInt(6, student.getId());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteStudentById(int id) {
        Connection connection = connectionManager.getConnection();
        Student student = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM students WHERE id=?");
        ) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteStudentByName(Student student) {
        return true;
    }
}