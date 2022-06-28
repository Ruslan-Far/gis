package com.ruslan.gis;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddingShapeController
{
    public static Stage addingShapeStage;

    @FXML
    private TextField shapeTextField;
    @FXML
    private TextField lengthTextField;
    @FXML
    private TextField squareTextField;
    @FXML
    private TextField perimeterTextField;

    public void initialize()
    {
        shapeTextField.setText(TableController.myShape.getShape());
        lengthTextField.setText(TableController.myShape.getLength().toString());
        squareTextField.setText(TableController.myShape.getSquare().toString());
        perimeterTextField.setText(TableController.myShape.getPerimeter().toString());
    }

    @FXML
    private void onClickOkBtn()
    {
        TableController.myShape.setShape(shapeTextField.getText());
        TableController.myShape.setLength(Double.parseDouble(lengthTextField.getText()));
        TableController.myShape.setSquare(Double.parseDouble(squareTextField.getText()));
        TableController.myShape.setPerimeter(Double.parseDouble(perimeterTextField.getText()));
        addingShapeStage.close();
    }

    @FXML
    private void onClickCancelBtn()
    {
        TableController.myShape = null;
        addingShapeStage.close();
    }
}
