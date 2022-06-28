package com.ruslan.gis.shapes;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.drutils.DrMyZona;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

import java.util.Optional;

public class MyZona
{
    private Ellipse ellipse;

    private double xgeoCenter;
    private double ygeoCenter;
    private double xgeoRadius;
    private double ygeoRadius;

    private double geoSquare;

    public MyZona(Ellipse ellipse)
    {
        this.ellipse = ellipse;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ГИС");
                alert.setHeaderText("Информация о зоне заражения в географической системе координат");
                alert.setContentText("Центральный x = " + xgeoCenter + "; Центральный y = " + ygeoCenter
                        + "\nГлубина зоны заражения = " + xgeoRadius + " км"
                        + "\nПлощадь = " + geoSquare + " км^2");
                ButtonType ok = new ButtonType("ОК");
                ButtonType delete = new ButtonType("Удалить");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete)
                {
                    PrimaryController.group.getChildren().remove(ellipse);
                    DrMyZona.myZonas.remove(MyZona.this);
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

    public double getGeoSquare() {
        return geoSquare;
    }

    public void setGeoSquare(double geoSquare) {
        this.geoSquare = geoSquare;
    }
}
