package com.ruslan.gis.utils;

import com.ruslan.gis.PrimaryController;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class RectGeo
{
    public static void determineCoordsOfPoint(MouseEvent mouseEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ГИС");
        int xrect = toRect(mouseEvent.getX());
        int yrect = toRect(mouseEvent.getY());
        if (PrimaryController.myPoints == null || PrimaryController.myPoints.size() == 0)
        {
            alert.setHeaderText("Координаты точки в прямоугольной системе координат");
            alert.setContentText("x = " + xrect + "; y = " + yrect);
        }
        else
        {
            double[] xygeo = toGeo(xrect, yrect);
            double xgeo = xygeo[0];
            double ygeo = xygeo[1];
            alert.setHeaderText("Координаты точки в географической системе координат");
            alert.setContentText("x = " + xgeo + "; y = " + ygeo);
        }
        alert.showAndWait();
    }

    public static int toRect(double n)
    {
        return (int) Math.round(n / PrimaryController.factor);
    }

    public static double[] toGeo(int xrect, int yrect)
    {
        double xgeo;
        double ygeo;

        if (xrect < PrimaryController.widthImg / 2 && yrect < PrimaryController.heightImg / 2)
        {
            xgeo = PrimaryController.myPoints.get(0).getXgeo() + ((xrect - PrimaryController.myPoints.get(0).getXrect()) * MyPoint.factorToGeoXTop);
            ygeo = PrimaryController.myPoints.get(0).getYgeo() - ((yrect - PrimaryController.myPoints.get(0).getYrect()) * MyPoint.factorToGeoYLeft);
        }
        else if (xrect > PrimaryController.widthImg / 2 && yrect < PrimaryController.heightImg / 2)
        {
            xgeo = PrimaryController.myPoints.get(0).getXgeo() + ((xrect - PrimaryController.myPoints.get(0).getXrect()) * MyPoint.factorToGeoXTop);
            ygeo = PrimaryController.myPoints.get(0).getYgeo() - ((yrect - PrimaryController.myPoints.get(1).getYrect()) * MyPoint.factorToGeoYRight);
        }
        else if (xrect > PrimaryController.widthImg / 2 && yrect > PrimaryController.heightImg / 2)
        {
            xgeo = PrimaryController.myPoints.get(0).getXgeo() + ((xrect - PrimaryController.myPoints.get(3).getXrect()) * MyPoint.factorToGeoXBottom);
            ygeo = PrimaryController.myPoints.get(0).getYgeo() - ((yrect - PrimaryController.myPoints.get(1).getYrect()) * MyPoint.factorToGeoYRight);
        }
        else
        {
            xgeo = PrimaryController.myPoints.get(0).getXgeo() + ((xrect - PrimaryController.myPoints.get(3).getXrect()) * MyPoint.factorToGeoXBottom);
            ygeo = PrimaryController.myPoints.get(0).getYgeo() - ((yrect - PrimaryController.myPoints.get(0).getYrect()) * MyPoint.factorToGeoYLeft);
        }

        return new double[] { xgeo, ygeo };
    }

    public static double toRectForRedraw(double n, boolean zoom)
    {
        if (zoom)
            return n * PrimaryController.FACTOR;
        else
            return n / PrimaryController.FACTOR;
    }

    public static int[] fromGeo(double xgeo, double ygeo)
    {
        int xrect;
        int yrect;

        if (xgeo < (PrimaryController.myPoints.get(0).getXgeo()
                + (PrimaryController.myPoints.get(1).getXgeo() - PrimaryController.myPoints.get(0).getXgeo()) / 2)
                && ygeo > (PrimaryController.myPoints.get(0).getYgeo()
                - (PrimaryController.myPoints.get(0).getYgeo() - PrimaryController.myPoints.get(3).getYgeo()) / 2))
        {
            xrect = (int) Math.round(PrimaryController.myPoints.get(0).getXrect() + ((xgeo - PrimaryController.myPoints.get(0).getXgeo()) / MyPoint.factorToGeoXTop));
            yrect = (int) Math.round(PrimaryController.myPoints.get(0).getYrect() + ((PrimaryController.myPoints.get(0).getYgeo() - ygeo) / MyPoint.factorToGeoYLeft));
        }
        else if (xgeo > (PrimaryController.myPoints.get(0).getXgeo()
                + (PrimaryController.myPoints.get(1).getXgeo() - PrimaryController.myPoints.get(0).getXgeo()) / 2)
                && ygeo > (PrimaryController.myPoints.get(0).getYgeo()
                - (PrimaryController.myPoints.get(0).getYgeo() - PrimaryController.myPoints.get(3).getYgeo()) / 2))
        {
            xrect = (int) Math.round(PrimaryController.myPoints.get(0).getXrect() + ((xgeo - PrimaryController.myPoints.get(0).getXgeo()) / MyPoint.factorToGeoXTop));
            yrect = (int) Math.round(PrimaryController.myPoints.get(0).getYrect() + ((PrimaryController.myPoints.get(1).getYgeo() - ygeo) / MyPoint.factorToGeoYRight));
        }
        else if (xgeo > (PrimaryController.myPoints.get(0).getXgeo()
                + (PrimaryController.myPoints.get(1).getXgeo() - PrimaryController.myPoints.get(0).getXgeo()) / 2)
                && ygeo < (PrimaryController.myPoints.get(1).getYgeo()
                - (PrimaryController.myPoints.get(1).getYgeo() - PrimaryController.myPoints.get(2).getYgeo()) / 2))
        {
            xrect = (int) Math.round(PrimaryController.myPoints.get(0).getXrect() + ((xgeo - PrimaryController.myPoints.get(3).getXgeo()) / MyPoint.factorToGeoXBottom));
            yrect = (int) Math.round(PrimaryController.myPoints.get(0).getYrect() + ((PrimaryController.myPoints.get(1).getYgeo() - ygeo) / MyPoint.factorToGeoYRight));
        }
        else
        {
            xrect = (int) Math.round(PrimaryController.myPoints.get(0).getXrect() + ((xgeo - PrimaryController.myPoints.get(3).getXgeo()) / MyPoint.factorToGeoXBottom));
            yrect = (int) Math.round(PrimaryController.myPoints.get(0).getYrect() + ((PrimaryController.myPoints.get(0).getYgeo() - ygeo) / MyPoint.factorToGeoYLeft));
        }

        return new int[] { xrect, yrect };
    }

    public static double fromRect(int n)
    {
        return n * PrimaryController.factor;
    }
}
