package com.restaurant.dao;

import com.restaurant.model.Drink;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {
    private DatabaseConnection dbConnection;

    public DrinkDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS drinks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "calories INTEGER NOT NULL," +
                "alcoholic INTEGER NOT NULL," +
                "base_price REAL NOT NULL" +
                ")";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insert(Drink drink) throws SQLException {
        String sql = "INSERT INTO drinks (name, calories, alcoholic, base_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, drink.getName());
            pstmt.setInt(2, drink.getCalories());
            pstmt.setInt(3, drink.isAlcoholic() ? 1 : 0);
            pstmt.setDouble(4, drink.getBasePrice());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                drink.setId(rs.getInt(1));
            }
        }
    }

    public void insertWithId(Drink drink) throws SQLException {
        String sql = "INSERT INTO drinks (id, name, calories, alcoholic, base_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, drink.getId());
            pstmt.setString(2, drink.getName());
            pstmt.setInt(3, drink.getCalories());
            pstmt.setInt(4, drink.isAlcoholic() ? 1 : 0);
            pstmt.setDouble(5, drink.getBasePrice());
            pstmt.executeUpdate();
        }
    }

    public List<Drink> findAll() throws SQLException {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drinks";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                drinks.add(mapRowToDrink(rs));
            }
        }
        return drinks;
    }

    public Drink findById(int id) throws SQLException {
        String sql = "SELECT * FROM drinks WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToDrink(rs);
            }
        }
        return null;
    }

    public void updatePrice(int id, double newPrice) throws SQLException {
        String sql = "UPDATE drinks SET base_price = ? WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    private Drink mapRowToDrink(ResultSet rs) throws SQLException {
        Drink drink = new Drink();
        drink.setId(rs.getInt("id"));
        drink.setName(rs.getString("name"));
        drink.setCalories(rs.getInt("calories"));
        drink.setAlcoholic(rs.getInt("alcoholic") == 1);
        drink.setBasePrice(rs.getDouble("base_price"));
        return drink;
    }
}

