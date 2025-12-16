package com.restaurant.dao;

import com.restaurant.model.City;
import com.restaurant.model.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    private DatabaseConnection dbConnection;

    public RestaurantDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS restaurants (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "city TEXT NOT NULL," +
                "chef_special_dish_id INTEGER" +
                ")";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insert(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO restaurants (name, city, chef_special_dish_id) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getCity().name());
            pstmt.setInt(3, restaurant.getChefSpecialDishId());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                restaurant.setId(rs.getInt(1));
            }
        }
    }

    public void insertWithId(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO restaurants (id, name, city, chef_special_dish_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurant.getId());
            pstmt.setString(2, restaurant.getName());
            pstmt.setString(3, restaurant.getCity().name());
            pstmt.setInt(4, restaurant.getChefSpecialDishId());
            pstmt.executeUpdate();
        }
    }

    public List<Restaurant> findAll() throws SQLException {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                restaurants.add(mapRowToRestaurant(rs));
            }
        }
        return restaurants;
    }

    public Restaurant findById(int id) throws SQLException {
        String sql = "SELECT * FROM restaurants WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToRestaurant(rs);
            }
        }
        return null;
    }

    private Restaurant mapRowToRestaurant(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(rs.getInt("id"));
        restaurant.setName(rs.getString("name"));
        restaurant.setCity(City.valueOf(rs.getString("city")));
        restaurant.setChefSpecialDishId(rs.getInt("chef_special_dish_id"));
        return restaurant;
    }
}

