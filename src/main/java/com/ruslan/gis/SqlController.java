package com.ruslan.gis;

import com.ruslan.gis.utils.DatabaseHandler;
import com.ruslan.gis.utils.MyShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlController
{
    public static Stage sqlStage;

    private DatabaseHandler databaseHandler;
    private ObservableList<MyShape> myShapes;

    @FXML
    private TableView<MyShape> resultTableView;
    @FXML
    private TextArea reqTextArea;

    public void initialize() throws SQLException, ClassNotFoundException
    {
        databaseHandler = new DatabaseHandler();
    }

    @FXML
    private void onClickSendBtn() throws SQLException
    {
        ResultSet resultSet = databaseHandler.sendAnyQuery(reqTextArea.getText());
        if (resultSet.next())
        {
            resultTableView.getColumns().clear();
            myShapes = FXCollections.observableArrayList();
            myShapes.add(new MyShape());
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++)
            {
                if (resultSet.getMetaData().getColumnTypeName(i).equals("serial"))
                {
                    TableColumn<MyShape, Integer> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                    column.setCellValueFactory(new PropertyValueFactory<>(resultSet.getMetaData().getColumnName(i)));
                    resultTableView.getColumns().add(column);
                    myShapes.get(0).setId(resultSet.getInt(i));
                }
                else if (resultSet.getMetaData().getColumnTypeName(i).equals("text"))
                {
                    TableColumn<MyShape, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                    column.setCellValueFactory(new PropertyValueFactory<>(resultSet.getMetaData().getColumnName(i)));
                    resultTableView.getColumns().add(column);
                    myShapes.get(0).setShape(resultSet.getString(i));
                }
                else
                {
                    TableColumn<MyShape, Double> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                    column.setCellValueFactory(new PropertyValueFactory<>(resultSet.getMetaData().getColumnName(i)));
                    resultTableView.getColumns().add(column);
                    if (resultSet.getMetaData().getColumnName(i).equals("length"))
                        myShapes.get(0).setLength(resultSet.getDouble(i));
                    else if (resultSet.getMetaData().getColumnName(i).equals("square"))
                        myShapes.get(0).setSquare(resultSet.getDouble(i));
                    else
                        myShapes.get(0).setPerimeter(resultSet.getDouble(i));
                }
            }

            while (resultSet.next())
            {
                myShapes.add(new MyShape());
                for (int i = 1; i <= columnCount; i++)
                {
                    if (resultSet.getMetaData().getColumnName(i).equals("id"))
                    {
                        myShapes.get(myShapes.size() - 1).setId(resultSet.getInt(i));
                    }
                    else if (resultSet.getMetaData().getColumnName(i).equals("shape"))
                    {
                        myShapes.get(myShapes.size() - 1).setShape(resultSet.getString(i));
                    }
                    else if (resultSet.getMetaData().getColumnName(i).equals("length"))
                    {
                        myShapes.get(myShapes.size() - 1).setLength(resultSet.getDouble(i));
                    }
                    else if (resultSet.getMetaData().getColumnName(i).equals("square"))
                    {
                        myShapes.get(myShapes.size() - 1).setSquare(resultSet.getDouble(i));
                    }
                    else
                    {
                        myShapes.get(myShapes.size() - 1).setPerimeter(resultSet.getDouble(i));
                    }
                }
            }
            resultTableView.setItems(myShapes);
        }
    }

    @FXML
    private void onClickCloseBtn()
    {
        myShapes = null;
        sqlStage.close();
    }
}
