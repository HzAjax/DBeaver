package org.example;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test",
                    "postgres",
                    "postgres");

            Database db = new DatabaseManager(connection);

            Attribute column1 = new Attribute("id", "uuid", "primary key");
            Attribute column2 = new Attribute("name", "varchar(30)");
            if (db.createTable("categories",column1, column2)){
                System.out.println("Table created");
            }

            db.deleteTable("categories");


            db.insertRow("categories", UUID.randomUUID(), "Фрукты");
            db.insertRow("categories", UUID.randomUUID(), "Овощи");
            db.insertRow("categories", UUID.randomUUID(), "Канцтовары");
            db.insertRow("categories", UUID.randomUUID(), "Спорттовары");


            ResultSet rs1 = db.getData("categories");
            List<Category> categories = new ArrayList<>();
            while (rs1.next()) {
                UUID id = rs1.getObject(1, UUID.class);
                String name = rs1.getString(2);
                Category category = new Category(id, name);
                categories.add(category);
            }

            System.out.println(categories);

            //db.updateRow("categories", "name", UUID.fromString("758e04c3-47b6-4a9c-8fcd-54269316dcc7"), "Офисные товары");

            //db.deleteRow("categories", UUID.fromString("758e04c3-47b6-4a9c-8fcd-54269316dcc7"));

            ResultSet rs2 = db.getData("categories");
            categories = new ArrayList<>();
            while (rs2.next()) {
                UUID id = rs2.getObject(1, UUID.class);
                String name = rs2.getString(2);
                Category category = new Category(id, name);
                categories.add(category);
            }

            System.out.println(categories);


            connection.close();
        } catch (SQLException e) {
            System.out.printf("Ошибка при подключении: %s%n", e.getLocalizedMessage());
        }



    }
}