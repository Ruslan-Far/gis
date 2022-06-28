package com.ruslan.gis;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddingBreakpointController
{
    public static Stage addingBreakpointStage;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField xgeoTextField;
    @FXML
    private TextField ygeoTextField;
    @FXML
    private TextField xrectTextField;
    @FXML
    private TextField yrectTextField;

    public void initialize()
    {
        nameTextField.setText(RegistrationImageController.myPoint.getName());
        xgeoTextField.setText(RegistrationImageController.myPoint.getXgeo().toString());
        ygeoTextField.setText(RegistrationImageController.myPoint.getYgeo().toString());
        xrectTextField.setText(RegistrationImageController.myPoint.getXrect().toString());
        yrectTextField.setText(RegistrationImageController.myPoint.getYrect().toString());
    }

    @FXML
    private void onClickOkBtn()
    {
        RegistrationImageController.myPoint.setName(nameTextField.getText());
        RegistrationImageController.myPoint.setXgeo(Double.parseDouble(xgeoTextField.getText()));
        RegistrationImageController.myPoint.setYgeo(Double.parseDouble(ygeoTextField.getText()));
        RegistrationImageController.myPoint.setXrect(Integer.parseInt(xrectTextField.getText()));
        RegistrationImageController.myPoint.setYrect(Integer.parseInt(yrectTextField.getText()));
        addingBreakpointStage.close();
    }

    @FXML
    private void onClickCancelBtn()
    {
        RegistrationImageController.myPoint = null;
        addingBreakpointStage.close();
    }
}
