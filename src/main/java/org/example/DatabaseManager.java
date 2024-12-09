package org.example;

import java.sql.*;
import java.util.Arrays;
import java.util.UUID;

public class DatabaseManager implements Database {

    private Connection connection;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createTable(String tableName, Attribute... attributes) throws SQLException {
        Statement statement = connection.createStatement();

        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableName);
        sb.append(" (");
        Arrays.stream(attributes)
                        .forEach(e ->{
                            sb.append(e.getName());
                            sb.append(" ");
                            sb.append(e.getType());
                            if(e.getConstrains() != null) {
                                sb.append(" ");
                                sb.append(e.getConstrains());
                            }
                            sb.append(",");
                        });
        sb.deleteCharAt(sb.length()-1);
        sb.append(");");

        try{
            statement.execute(sb.toString());
            return true;
        }catch(SQLException e){
            System.out.println("Ошибка при создании таблицы:" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTable(String tableName) throws SQLException {
        Statement statement = connection.createStatement();

        StringBuilder sb = new StringBuilder();
        sb.append("drop table ");
        sb.append(tableName);

        try{
            statement.execute(sb.toString());
            return true;
        }catch(SQLException e){
            System.out.println("Ошибка при удалении таблицы:" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertRow(String tableName, Object... values) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(tableName);
        sb.append(" values(");
        Arrays.stream(values)
                        .forEach(e ->{
                            sb.append("?,");
                        });
        sb.deleteCharAt(sb.length()-1);
        sb.append(");");
        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

        for(int i = 0; i < values.length; i++){
            if(values[i] instanceof String){
                preparedStatement.setString(i+1, (String) values[i]);
            }else if(values[i] instanceof Integer){
                preparedStatement.setInt(i+1, (Integer) values[i]);
            }else if(values[i] instanceof Double){
                preparedStatement.setDouble(i+1, (Double) values[i]);
            }else {
                preparedStatement.setObject(i+1, values[i]);
            }

        }

        try{
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Ошибка при вставке строки" + e.getMessage());
            return false;
        }
    }

    @Override
    public ResultSet getData(String tableName) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(tableName);
        sb.append(";");

        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());


        return preparedStatement.executeQuery();
    }

    @Override
    public boolean updateRow(String tableName, String columnName, UUID id, String value) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableName);
        sb.append(" set ");
        sb.append(columnName);
        sb.append(" = ? where id = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

        preparedStatement.setString(1, value);
        preparedStatement.setObject(2, id);

        try{
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Ошибка при обновлении строки" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteRow(String tableName, UUID id) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(tableName);
        sb.append(" where id = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

        preparedStatement.setObject(1, id);

        try{
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Ошибка при удалении строки" + e.getMessage());
            return false;
        }
    }
}
