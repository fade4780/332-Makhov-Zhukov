package com.restaurant.dao;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private DatabaseConnection dbConnection;

    public OrderDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "restaurant_id INTEGER NOT NULL," +
                "order_time TEXT NOT NULL," +
                "total_amount REAL NOT NULL," +
                "discount_amount REAL NOT NULL" +
                ")";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }

        sql = "CREATE TABLE IF NOT EXISTS order_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "dish_id INTEGER," +
                "drink_id INTEGER," +
                "quantity INTEGER NOT NULL," +
                "price REAL NOT NULL," +
                "FOREIGN KEY (order_id) REFERENCES orders(id)" +
                ")";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void save(Order order) throws SQLException {
        String sql = "INSERT INTO orders (restaurant_id, order_time, total_amount, discount_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getRestaurant().getId());
            pstmt.setString(2, order.getOrderTime().toString());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setDouble(4, order.getDiscountAmount());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }
        }

        sql = "INSERT INTO order_items (order_id, dish_id, drink_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (OrderItem item : order.getItems()) {
                pstmt.setInt(1, order.getId());
                if (item.isDish()) {
                    pstmt.setInt(2, item.getDish().getId());
                    pstmt.setNull(3, Types.INTEGER);
                } else {
                    pstmt.setNull(2, Types.INTEGER);
                    pstmt.setInt(3, item.getDrink().getId());
                }
                pstmt.setInt(4, item.getQuantity());
                pstmt.setDouble(5, item.getPrice());
                pstmt.executeUpdate();
            }
        }
    }
}

