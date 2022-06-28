package com.ruslan.gis.utils;

import com.ruslan.gis.TableController;

import java.sql.*;

public class DatabaseHandler extends Configs
{
    private Connection dbConnection;
    private static String selectReq = "SELECT * FROM shapes";
    private static String insertReq = "INSERT INTO shapes (shape, length, square, perimeter) " +
            "VALUES (?, ?, ?, ?) " +
            "RETURNING id, shape, length, square, perimeter";
    private static String updateReq = "UPDATE shapes " +
            "SET shape = ?, length = ?, square = ?, perimeter = ? " +
            "WHERE id = ? " +
            "RETURNING id, shape, length, square, perimeter";
    private static String deleteReq = "DELETE FROM shapes " +
            "WHERE id = ? " +
            "RETURNING id, shape, length, square, perimeter";

    public DatabaseHandler() throws ClassNotFoundException, SQLException
    {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public void selectQuery() throws SQLException
    {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectReq);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            TableController.myShapes.add(new MyShape(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
    }

    public void insertQuery() throws SQLException
    {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertReq);
        preparedStatement.setString(1, TableController.myShape.getShape());
        preparedStatement.setDouble(2, TableController.myShape.getLength());
        preparedStatement.setDouble(3, TableController.myShape.getSquare());
        preparedStatement.setDouble(4, TableController.myShape.getPerimeter());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            TableController.myShape = null;
        else
            TableController.myShape = new MyShape(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
    }

    public void updateQuery() throws SQLException
    {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateReq);
        preparedStatement.setString(1, TableController.myShape.getShape());
        preparedStatement.setDouble(2, TableController.myShape.getLength());
        preparedStatement.setDouble(3, TableController.myShape.getSquare());
        preparedStatement.setDouble(4, TableController.myShape.getPerimeter());
        preparedStatement.setInt(5, TableController.myShape.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            TableController.myShape = null;
        else
            TableController.myShape = new MyShape(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
    }

    public void deleteQuery() throws SQLException
    {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteReq);
        preparedStatement.setInt(1, TableController.myShape.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            TableController.myShape = null;
        else
            TableController.myShape = new MyShape(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
    }

    public ResultSet sendAnyQuery(String req) throws SQLException
    {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(req);
        return preparedStatement.executeQuery();
    }
}
