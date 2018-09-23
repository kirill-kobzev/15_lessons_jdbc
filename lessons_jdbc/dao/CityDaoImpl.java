package lessons_jdbc.dao;

import lessons_jdbc.connectionManager.ConnectionManager;
import lessons_jdbc.connectionManager.ConnectionManagerJdbcImpl;
import lessons_jdbc.pojo.City;
import lessons_jdbc.pojo.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CityDaoImpl implements CityDao {
    private static ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();
    public City getCitById(Integer id) {
        Connection connection = connectionManager.getConnection();
        City city = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * from city WHERE id = ?");
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    city = new City(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("citizens"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return city;
    }
}
