package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        // Load the MySQL Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL driver loaded successfully.");

            // 1. Open a connection to the database
            String jdbcURL = "jdbc:mysql://localhost:3306/northwind";
            String username = "    ";
            String password = "         ";

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database!");

            // 2. Create a statement
            Statement statement = connection.createStatement();

            // Define your query
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

            // Execute your query
            ResultSet results = statement.executeQuery(query);

            // 3. Process the results
            System.out.println("Product Details:");
            while (results.next()) {
                int productID = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                System.out.println("---------------------------");
                System.out.println("Product Id: " + productID);
                System.out.println("Name: " + productName);
                System.out.println("Price: " + unitPrice);
                System.out.println("Stock: " + unitsInStock);
            }


            // 4. Close the connection
            connection.close();
            System.out.println("Connection closed.");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}