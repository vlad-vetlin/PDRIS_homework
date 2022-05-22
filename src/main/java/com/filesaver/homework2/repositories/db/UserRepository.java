package com.filesaver.homework2.repositories.db;

import com.filesaver.homework2.domain.User;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final HikariDataSource dataSource;

    public UserRepository(HikariDataSource hikariDataSource) {
        this.dataSource = hikariDataSource;
    }

    public User create(User user) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO users (name, email, phone, password) VALUES (" +
                    "'" + user.getName() + "', " +
                    "'" + user.getEmail() + "', " +
                    "'" + user.getPhone() + "', " +
                    "'" + user.getPassword() + "')";
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException sqlException) {
            System.out.println("Sql exception: " + sqlException.getMessage());
        }

        return user;
    }

    public User update(User user) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(
                    "UPDATE usets SET name = '" +
                            user.getName() +
                            "' email = '" +
                            user.getEmail() +
                            "' phone = '" +
                            user.getPhone() +
                            "' password = '" +
                            user.getPassword() +
                            "'");

            connection.commit();
            return user;
        } catch (SQLException sqlException) {
            System.out.println("Sql exception: " + sqlException.getMessage());
        }

        return user;
    }

    public List<User> findByEmail(String email) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users WHERE email = '" + email + "'"
            );

            return getList(resultSet);
        } catch (SQLException sqlException) {
            System.out.println("Sql exception: " + sqlException.getMessage());
        }

        return new ArrayList<>();
    }

    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT TOP 1 * FROM users WHERE id = '" + id + "'"
            );

            if (resultSet.next()) {
                return Optional.of(getElement(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException sqlException) {
            System.out.println("Sql exception: " + sqlException.getMessage());
        }

        return Optional.empty();
    }

    public List<User> findAll() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users"
            );

            return getList(resultSet);

        } catch (SQLException sqlException) {
            System.out.println("Sql exception: " + sqlException.getMessage());
        }

        return new ArrayList<>();
    }

    public void delete(User user) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM users WHERE id = '" + user.getId() + "'");
            connection.commit();
        } catch (SQLException sqlException) {
            System.out.println("Sql exception: " + sqlException.getMessage());
        }
    }

    private List<User> getList(ResultSet resultSet) throws SQLException {
        List<User> response = new ArrayList<>();

        while (resultSet.next()) {
            response.add(getElement(resultSet));
        }

        return response;
    }

    private User getElement(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong(1));
        user.setName(resultSet.getString(2));
        user.setEmail(resultSet.getString(3));
        user.setPhone(resultSet.getString(4));
        user.setPassword(resultSet.getString(5));

        return user;
    }
}
