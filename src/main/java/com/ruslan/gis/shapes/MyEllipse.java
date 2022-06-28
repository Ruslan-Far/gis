package com.ruslan.gis.shapes;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.drutils.DrMyEllipse;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

import java.util.Optional;

public class MyEllipse
{
    private Ellipse ellipse;

    private boolean isGeo = false;

    private double xgeoCenter;
    private double ygeoCenter;
    private double xgeoRadius;
    private double ygeoRadius;

    private int xrectCenter;
    private int yrectCenter;
    private int xrectRadius;
    private int yrectRadius;

    private double square;

    public MyEllipse(Ellipse ellipse)
    {
        this.ellipse = ellipse;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ГИС");
                if (!isGeo)
                {
                    alert.setHeaderText("Информация об эллипсе в прямоугольной системе координат");
                    alert.setContentText("Центральный x = " + xrectCenter + "; Центральный y = " + yrectCenter
                            + "\nРадиус x = " + xrectRadius + " ; Радиус y = " + yrectRadius
                            + "\nПлощадь = " + square);
                }
                else
                {
                    alert.setHeaderText("Информация об эллипсе в географической системе координат");
                    alert.setContentText("Центральный x = " + xgeoCenter + "; Центральный y = " + ygeoCenter
                            + "\nРадиус x = " + xgeoRadius + " км ; Радиус y = " + ygeoRadius + " км"
                            + "\nПлощадь = " + square + " км^2");
                }
                ButtonType ok = new ButtonType("ОК");
                ButtonType delete = new ButtonType("Удалить");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete)
                {
                    PrimaryController.group.getChildren().remove(ellipse);
                    DrMyEllipse.myEllipses.remove(MyEllipse.this);
                }
            }
        };
        this.ellipse.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void setEllipse(Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    public boolean isGeo() {
        return isGeo;
    }

    public void setGeo(boolean geo) {
        isGeo = geo;
    }

    public double getXgeoCenter() {
        return xgeoCenter;
    }

    public void setXgeoCenter(double xgeoCenter) {
        this.xgeoCenter = xgeoCenter;
    }

    public double getYgeoCenter() {
        return ygeoCenter;
    }

    public void setYgeoCenter(double ygeoCenter) {
        this.ygeoCenter = ygeoCenter;
    }

    public double getXgeoRadius() {
        return xgeoRadius;
    }

    public void setXgeoRadius(double xgeoRadius) {
        this.xgeoRadius = xgeoRadius;
    }

    public double getYgeoRadius() {
        return ygeoRadius;
    }

    public void setYgeoRadius(double ygeoRadius) {
        this.ygeoRadius = ygeoRadius;
    }

    public int getXrectCenter() {
        return xrectCenter;
    }

    public void setXrectCenter(int xrectCenter) {
        this.xrectCenter = xrectCenter;
    }

    public int getYrectCenter() {
        return yrectCenter;
    }

    public void setYrectCenter(int yrectCenter) {
        this.yrectCenter = yrectCenter;
    }

    public int getXrectRadius() {
        return xrectRadius;
    }

    public void setXrectRadius(int xrectRadius) {
        this.xrectRadius = xrectRadius;
    }

    public int getYrectRadius() {
        return yrectRadius;
    }

    public void setYrectRadius(int yrectRadius) {
        this.yrectRadius = yrectRadius;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }
}
