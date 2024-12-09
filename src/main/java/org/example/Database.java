package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface Database {
    // DDL
    boolean createTable(String tableName, Attribute... attributes) throws SQLException;
    boolean deleteTable(String tableName) throws SQLException;

    //DML
    boolean insertRow(String tableName, Object... values) throws SQLException;
    ResultSet getData(String tableName) throws SQLException;
    boolean updateRow(String tableName, String columnName, UUID id, String value) throws SQLException;
    boolean deleteRow(String tableName, UUID id) throws SQLException;
}
