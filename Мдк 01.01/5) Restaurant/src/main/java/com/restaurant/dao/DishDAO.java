package com.restaurant.dao;

import com.restaurant.model.Dish;
import com.restaurant.model.DishType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {
    private DatabaseConnection dbConnection;

    public DishDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS dishes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "vegetarian INTEGER NOT NULL," +
                "calories INTEGER NOT NULL," +
                "type TEXT NOT NULL," +
                "base_price REAL NOT NULL," +
                "is_business_lunch INTEGER NOT NULL," +
                "is_morning_menu INTEGER NOT NULL," +
                "is_chef_special INTEGER NOT NULL" +
                ")";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insert(Dish dish) throws SQLException {
        String sql = "INSERT INTO dishes (name, vegetarian, calories, type, base_price, " +
                "is_business_lunch, is_morning_menu, is_chef_special) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, dish.getName());
            pstmt.setInt(2, dish.isVegetarian() ? 1 : 0);
            pstmt.setInt(3, dish.getCalories());
            pstmt.setString(4, dish.getType().name());
            pstmt.setDouble(5, dish.getBasePrice());
            pstmt.setInt(6, dish.isBusinessLunch() ? 1 : 0);
            pstmt.setInt(7, dish.isMorningMenu() ? 1 : 0);
            pstmt.setInt(8, dish.isChefSpecial() ? 1 : 0);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                dish.setId(rs.getInt(1));
            }
        }
    }

    public void insertWithId(Dish dish) throws SQLException {
        String sql = "INSERT INTO dishes (id, name, vegetarian, calories, type, base_price, " +
                "is_business_lunch, is_morning_menu, is_chef_special) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dish.getId());
            pstmt.setString(2, dish.getName());
            pstmt.setInt(3, dish.isVegetarian() ? 1 : 0);
            pstmt.setInt(4, dish.getCalories());
            pstmt.setString(5, dish.getType().name());
            pstmt.setDouble(6, dish.getBasePrice());
            pstmt.setInt(7, dish.isBusinessLunch() ? 1 : 0);
            pstmt.setInt(8, dish.isMorningMenu() ? 1 : 0);
            pstmt.setInt(9, dish.isChefSpecial() ? 1 : 0);
            pstmt.executeUpdate();
        }
    }

    public List<Dish> findAll() throws SQLException {
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT * FROM dishes";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dishes.add(mapRowToDish(rs));
            }
        }
        return dishes;
    }

    public Dish findById(int id) throws SQLException {
        String sql = "SELECT * FROM dishes WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToDish(rs);
            }
        }
        return null;
    }

    public void updatePrice(int id, double newPrice) throws SQLException {
        String sql = "UPDATE dishes SET base_price = ? WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    private Dish mapRowToDish(ResultSet rs) throws SQLException {
        Dish dish = new Dish();
        dish.setId(rs.getInt("id"));
        dish.setName(rs.getString("name"));
        dish.setVegetarian(rs.getInt("vegetarian") == 1);
        dish.setCalories(rs.getInt("calories"));
        dish.setType(DishType.valueOf(rs.getString("type")));
        dish.setBasePrice(rs.getDouble("base_price"));
        dish.setBusinessLunch(rs.getInt("is_business_lunch") == 1);
        dish.setMorningMenu(rs.getInt("is_morning_menu") == 1);
        dish.setChefSpecial(rs.getInt("is_chef_special") == 1);
        return dish;
    }
}

