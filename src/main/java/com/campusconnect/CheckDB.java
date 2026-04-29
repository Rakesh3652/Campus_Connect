package com.campusconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

public class CheckDB {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:1234/CampusConnect";
        String user = "postgres";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
            
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, "public", "%", new String[] {"TABLE"});
            System.out.println("Tables:");
            while (tables.next()) {
                System.out.println("- " + tables.getString("TABLE_NAME"));
            }
            
            try (Statement stmt = conn.createStatement()) {
                System.out.println("\n--- Users ---");
                ResultSet rsUser = stmt.executeQuery("SELECT id, email FROM \"user\" LIMIT 5");
                while (rsUser.next()) {
                    System.out.println("User ID: " + rsUser.getLong("id") + ", Email: " + rsUser.getString("email"));
                }
            } catch (Exception e) {
                System.out.println("Error querying users (user table): " + e.getMessage());
            }

            try (Statement stmt = conn.createStatement()) {
                ResultSet rsUser = stmt.executeQuery("SELECT id, email FROM users LIMIT 5");
                while (rsUser.next()) {
                    System.out.println("User ID: " + rsUser.getLong("id") + ", Email: " + rsUser.getString("email"));
                }
            } catch (Exception e) {
                System.out.println("Error querying users (users table): " + e.getMessage());
            }

            try (Statement stmt = conn.createStatement()) {
                System.out.println("\n--- Vendors ---");
                ResultSet rsVendor = stmt.executeQuery("SELECT id FROM vendor LIMIT 5");
                while (rsVendor.next()) {
                    System.out.println("Vendor ID: " + rsVendor.getLong("id"));
                }
            } catch (Exception e) {
                System.out.println("Error querying vendors: " + e.getMessage());
            }

            try (Statement stmt = conn.createStatement()) {
                System.out.println("\n--- Events ---");
                ResultSet rsEvent = stmt.executeQuery("SELECT id, title FROM event LIMIT 5");
                while (rsEvent.next()) {
                    System.out.println("Event ID: " + rsEvent.getLong("id") + ", Title: " + rsEvent.getString("title"));
                }
            } catch (Exception e) {
                System.out.println("Error querying events: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
