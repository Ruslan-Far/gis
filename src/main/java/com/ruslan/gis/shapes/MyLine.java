package com.ruslan.gis.shapes;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.drutils.DrMyLine;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.Optional;

public class MyLine
{
    private Line line;

    private boolean isGeo = false;

    private double x1geo;
    private double y1geo;
    private double x2geo;
    private double y2geo;

    private int x1rect;
    private int y1rect;
    private int x2rect;
    private int y2rect;

    private double length;

    public MyLine(Line line)
    {
        this.line = line;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ГИС");
                if (!isGeo)
                {
                    alert.setHeaderText("Информация о линии в прямоугольной системе координат");
                    alert.setContentText("x1 = " + x1rect + "; y1 = " + y1rect + "\n" +
                            "x2 = " + x2rect + "; y2 = " + y2rect + "\nДлина = " + length);
                }
                else
                {
                    alert.setHeaderText("Информация о линии в географической системе координат");
                    alert.setContentText("x1 = " + x1geo + "; y1 = " + y1geo + "\n" +
                            "x2 = " + x2geo + "; y2 = " + y2geo + "\nДлина = " + length + " км");
                }
                ButtonType ok = new ButtonType("ОК");
                ButtonType delete = new ButtonType("Удалить");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete)
                {
                    PrimaryController.group.getChildren().remove(line);
                    DrMyLine.myLines.remove(MyLine.this);
                }
            }
        };
        this.line.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public boolean isGeo() {
        return isGeo;
    }

    public void setGeo(boolean geo) {
        isGeo = geo;
    }

    public double getX1geo() {
        return x1geo;
    }

    public void setX1geo(double x1geo) {
        this.x1geo = x1geo;
    }

    public double getY1geo() {
        return y1geo;
    }

    public void setY1geo(double y1geo) {
        this.y1geo = y1geo;
    }

    public double getX2geo() {
        return x2geo;
    }

    public void setX2geo(double x2geo) {
        this.x2geo = x2geo;
    }

    public double getY2geo() {
        return y2geo;
    }

    public void setY2geo(double y2geo) {
        this.y2geo = y2geo;
    }

    public int getX1rect() {
        return x1rect;
    }

    public void setX1rect(int x1rect) {
        this.x1rect = x1rect;
    }

    public int getY1rect() {
        return y1rect;
    }

    public void setY1rect(int y1rect) {
        this.y1rect = y1rect;
    }

    public int getX2rect() {
        return x2rect;
    }

    public void setX2rect(int x2rect) {
        this.x2rect = x2rect;
    }

    public int getY2rect() {
        return y2rect;
    }

    public void setY2rect(int y2rect) {
        this.y2rect = y2rect;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
