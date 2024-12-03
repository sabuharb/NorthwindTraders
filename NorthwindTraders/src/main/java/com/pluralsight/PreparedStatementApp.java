package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class PreparedStatementApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind"; // Database URL
        String user = args[0]; // Username passed as a command-line argument
        String password = args[1]; // Password passed as a command-line argument
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Home screen menu
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();

            if (option == 0) {
                System.out.println("Exiting the application...");
                break; // Exit the loop
            }

            switch (option) {
                case 1:
                    displayProducts(url, user, password);
                    break;
                case 2:
                    displayCustomers(url, user, password);
                    break;
                case 3:
                    displayCategories(url, user, password, scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    // Display all products
    private static void displayProducts(String url, String user, String password) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            System.out.printf("%-5s %-25s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----------------------------------------------------------");

            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-25s %-10.2f %-10d%n", productId, productName, unitPrice, unitsInStock);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Display all customers
    private static void displayCustomers(String url, String user, String password) {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            System.out.printf("%-25s %-30s %-15s %-15s %-15s%n", "Contact Name", "Company Name", "City", "Country", "Phone");
            System.out.println("-------------------------------------------------------------------------------");

            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf("%-25s %-30s %-15s %-15s %-15s%n", contactName, companyName, city, country, phone);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Display all categories
    private static void displayCategories(String url, String user, String password, Scanner scanner) {
        String query = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            System.out.printf("%-5s %-25s%n", "Id", "Category Name");
            System.out.println("-------------------------------");

            while (results.next()) {
                int categoryId = results.getInt("CategoryID");
                String categoryName = results.getString("CategoryName");

                System.out.printf("%-5d %-25s%n", categoryId, categoryName);
            }

            // Prompt user for a CategoryID
            System.out.print("Enter a CategoryID to view products in that category: ");
            int selectedCategoryId = scanner.nextInt();

            // Display products in the selected category
            displayProductsByCategory(url, user, password, selectedCategoryId);

        } catch (SQLException e) {
            System.err.println("Error retrieving categories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Display products by category
    private static void displayProductsByCategory(String url, String user, String password, int categoryId) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE CategoryID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);
            ResultSet results = statement.executeQuery();

            System.out.printf("%-5s %-25s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----------------------------------------------------------");

            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-25s %-10.2f %-10d%n", productId, productName, unitPrice, unitsInStock);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving products by category: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
