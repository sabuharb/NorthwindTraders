package com.pluralsight;

import java.sql.*;

public class PreparedStatementApp {
    public static void main(String[] args) {
        // Step 1: Define the database connection details
        String url = "jdbc:mysql://127.0.0.1:3306/northwind"; // Database URL
        String user = args[0]; // Username (passed as a command-line argument)
        String password = args[1]; // Password (passed as a command-line argument)

        // Step 2: Define the SQL query to retrieve product details
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try {
            // Step 3: Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

            // Step 4: Use a PreparedStatement to execute the query
            PreparedStatement statement = connection.prepareStatement(query);

            // Step 5: Execute the query and retrieve results
            ResultSet results = statement.executeQuery();

            // Step 6: Process the results
            System.out.println("Product Details:");

            // Option 1: Display as Stacked Information
            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                // Print in stacked format
                System.out.println("---------------------------");
                System.out.println("Product Id: " + productId);
                System.out.println("Name: " + productName);
                System.out.println("Price: " + unitPrice);
                System.out.println("Stock: " + unitsInStock);
            }

            // Uncomment the following block for Option 2: Rows of Information

            System.out.printf("%-5s %-20s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----------------------------------------------");
            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                // Print in row format
                System.out.printf("%-5d %-20s %-10.2f %-10d%n", productId, productName, unitPrice, unitsInStock);
            }

            // Step 7: Close resources
            results.close();
            statement.close();
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}