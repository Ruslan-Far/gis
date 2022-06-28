package com.ruslan.gis.drutils;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.ZonaController;
import com.ruslan.gis.shapes.MyZona;
import com.ruslan.gis.utils.RectGeo;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

public class DrMyZona
{
    public static ArrayList<MyZona> myZonas;
    public static int countPointsZona;

    private static MyZona myZona;

    public static void drawZona(double xgeoRadius, double geoSquare)
    {
        myZona = new MyZona(new Ellipse());
        double ygeoRadius = geoSquare / Math.PI / xgeoRadius;

        myZona.getEllipse().setCenterX(ZonaController.xcenter);
        myZona.getEllipse().setCenterY(ZonaController.ycenter);

        double[] xygeo = RectGeo.toGeo(RectGeo.toRect(ZonaController.xcenter), RectGeo.toRect(ZonaController.ycenter));
        myZona.setXgeoCenter(xygeo[0]);
        myZona.setYgeoCenter(xygeo[1]);

        double xgeoSubtract = PrimaryController.fromXkm(xgeoRadius);
        int[] xyrect = RectGeo.fromGeo(myZona.getXgeoCenter() + xgeoSubtract, myZona.getYgeoCenter());
        double xradius = Math.abs(RectGeo.fromRect(xyrect[0]) - myZona.getEllipse().getCenterX());
        myZona.getEllipse().setRadiusX(xradius);
        myZona.setXgeoRadius(xgeoRadius);

        double ygeoSubtract = PrimaryController.fromYkm(ygeoRadius);
        xyrect = RectGeo.fromGeo(myZona.getXgeoCenter(), myZona.getYgeoCenter() - ygeoSubtract);
        double yradius = Math.abs(RectGeo.fromRect(xyrect[1]) - myZona.getEllipse().getCenterY());
        myZona.getEllipse().setRadiusY(yradius);
        myZona.setYgeoRadius(ygeoRadius);

        myZona.getEllipse().setStrokeWidth(PrimaryController.factor);
        myZona.getEllipse().setStroke(Color.RED);
        myZona.getEllipse().setFill(Color.ROSYBROWN);
        myZona.setGeoSquare(geoSquare);
        myZonas.add(myZona);
        PrimaryController.group.getChildren().add(myZona.getEllipse());
        countPointsZona = 0;
    }

    public static void redrawZona(boolean zoom)
    {
        if (myZonas != null)
        {
            for (int i = 0; i < myZonas.size(); i++)
                PrimaryController.group.getChildren().remove(myZonas.get(i).getEllipse());
            for (int i = 0; i < myZonas.size(); i++)
            {
                myZonas.get(i).getEllipse().setCenterX(RectGeo.toRectForRedraw(myZonas.get(i).getEllipse().getCenterX(), zoom));
                myZonas.get(i).getEllipse().setCenterY(RectGeo.toRectForRedraw(myZonas.get(i).getEllipse().getCenterY(), zoom));
                myZonas.get(i).getEllipse().setRadiusX(RectGeo.toRectForRedraw(myZonas.get(i).getEllipse().getRadiusX(), zoom));
                myZonas.get(i).getEllipse().setRadiusY(RectGeo.toRectForRedraw(myZonas.get(i).getEllipse().getRadiusY(), zoom));
                myZonas.get(i).getEllipse().setStrokeWidth(PrimaryController.factor);

                PrimaryController.group.getChildren().add(myZonas.get(i).getEllipse());
            }
        }
    }
}
