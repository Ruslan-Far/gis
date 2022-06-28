package com.ruslan.gis.shapes;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.drutils.DrMyPolygon;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Optional;

public class MyPolygon
{
    private Polygon polygon;
    private ArrayList<MyLine> myLines;

    private double perimeter;

    public MyPolygon(Polygon polygon)
    {
        this.polygon = polygon;
        this.myLines = new ArrayList<>();
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ГИС");
                if (!myLines.get(0).isGeo())
                {
                    alert.setHeaderText("Информация о полигоне в прямоугольной системе координат");
                    alert.setContentText(
                        "Стартовый x = " + myLines.get(0).getX1rect() + "; Стартовый y = " + myLines.get(0).getY1rect()
                                + "\nПериметр = " + perimeter);
                }
                else
                {
                    alert.setHeaderText("Информация о полигоне в географической системе координат");
                    alert.setContentText(
                        "Стартовый x = " + myLines.get(0).getX1geo() + "; Стартовый y = " + myLines.get(0).getY1geo()
                            + "\nПериметр = " + perimeter + " км");
                }
                ButtonType ok = new ButtonType("ОК");
                ButtonType delete = new ButtonType("Удалить");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete)
                {
                    for (int i = 0; i < myLines.size(); i++)
                        PrimaryController.group.getChildren().remove(myLines.get(i).getLine());
                    PrimaryController.group.getChildren().remove(polygon);
                    DrMyPolygon.myPolygons.remove(MyPolygon.this);
                }
            }
        };
        this.polygon.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public ArrayList<MyLine> getMyLines() {
        return myLines;
    }

    public void setMyLines(ArrayList<MyLine> myLines) {
        this.myLines = myLines;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }
}