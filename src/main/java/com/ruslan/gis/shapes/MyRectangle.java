package com.ruslan.gis.shapes;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.drutils.DrMyRectangle;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class MyRectangle
{
    private Rectangle rectangle;

    private boolean isGeo = false;

    private double xgeoLeftTop;
    private double ygeoLeftTop;
    private double geoWidth;
    private double geoHeight;

    private int xrectLeftTop;
    private int yrectLeftTop;
    private int rectWidth;
    private int rectHeight;

    private double square;
    private double perimeter;

    public MyRectangle(Rectangle rectangle)
    {
        this.rectangle = rectangle;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ГИС");
                if (!isGeo)
                {
                    alert.setHeaderText("Информация о прямоугольнике в прямоугольной системе координат");
                    alert.setContentText("Координата левого верхнего угла:"
                            + "\nx = " + xrectLeftTop + " ; y = " + yrectLeftTop
                            + "\nШирина = " + rectWidth + " ; Высота = " + rectHeight
                            + "\nПлощадь = " + square + "\nПериметр = " + perimeter);
                }
                else
                {
                    alert.setHeaderText("Информация о прямоугольнике в географической системе координат");
                    alert.setContentText("Координата левого верхнего угла:"
                            + "\nx = " + xgeoLeftTop + " ; y = " + ygeoLeftTop
                            + "\nШирина = " + geoWidth + " км ; Высота = " + geoHeight + " км"
                            + "\nПлощадь = " + square + " км^2\nПериметр = " + perimeter + " км");
                }
                ButtonType ok = new ButtonType("ОК");
                ButtonType delete = new ButtonType("Удалить");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete)
                {
                    PrimaryController.group.getChildren().remove(rectangle);
                    DrMyRectangle.myRectangles.remove(MyRectangle.this);
                }
            }
        };
        this.rectangle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isGeo() {
        return isGeo;
    }

    public void setGeo(boolean geo) {
        isGeo = geo;
    }

    public double getXgeoLeftTop() {
        return xgeoLeftTop;
    }

    public void setXgeoLeftTop(double xgeoLeftTop) {
        this.xgeoLeftTop = xgeoLeftTop;
    }

    public double getYgeoLeftTop() {
        return ygeoLeftTop;
    }

    public void setYgeoLeftTop(double ygeoLeftTop) {
        this.ygeoLeftTop = ygeoLeftTop;
    }

    public double getGeoWidth() {
        return geoWidth;
    }

    public void setGeoWidth(double geoWidth) {
        this.geoWidth = geoWidth;
    }

    public double getGeoHeight() {
        return geoHeight;
    }

    public void setGeoHeight(double geoHeight) {
        this.geoHeight = geoHeight;
    }

    public int getXrectLeftTop() {
        return xrectLeftTop;
    }

    public void setXrectLeftTop(int xrectLeftTop) {
        this.xrectLeftTop = xrectLeftTop;
    }

    public int getYrectLeftTop() {
        return yrectLeftTop;
    }

    public void setYrectLeftTop(int yrectLeftTop) {
        this.yrectLeftTop = yrectLeftTop;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth) {
        this.rectWidth = rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public void setRectHeight(int rectHeight) {
        this.rectHeight = rectHeight;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }
}
