package com.ruslan.gis.drutils;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.utils.RectGeo;
import com.ruslan.gis.shapes.MyEllipse;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

public class DrMyEllipse
{
    public static ArrayList<MyEllipse> myEllipses;
    public static int countPointsEllipse;

    private static MyEllipse myEllipse;

    public static void drawEllipse(double x, double y)
    {
        if (countPointsEllipse == 1)
        {
            myEllipse = new MyEllipse(new Ellipse());
            myEllipse.getEllipse().setCenterX(x);
            myEllipse.getEllipse().setCenterY(y);
            myEllipse.setXrectCenter(RectGeo.toRect(x));
            myEllipse.setYrectCenter(RectGeo.toRect(y));
            if (!(PrimaryController.myPoints == null || PrimaryController.myPoints.size() == 0))
            {
                double[] xygeo = RectGeo.toGeo(myEllipse.getXrectCenter(), myEllipse.getYrectCenter());
                myEllipse.setXgeoCenter(xygeo[0]);
                myEllipse.setYgeoCenter(xygeo[1]);
                myEllipse.setGeo(true);
            }
            countPointsEllipse += 1;
        }
        else if (countPointsEllipse == 2)
        {
            double xradius = Math.abs(x - myEllipse.getEllipse().getCenterX());
            myEllipse.getEllipse().setRadiusX(xradius);
            myEllipse.setXrectRadius(RectGeo.toRect(xradius));
            if (myEllipse.isGeo())
            {
                double[] xygeo = RectGeo.toGeo(RectGeo.toRect(x), RectGeo.toRect(y));
                myEllipse.setXgeoRadius(PrimaryController.toXkm(myEllipse.getXgeoCenter(), xygeo[0]));
            }
            countPointsEllipse += 1;
        }
        else
        {
            double yradius = Math.abs(y - myEllipse.getEllipse().getCenterY());
            myEllipse.getEllipse().setRadiusY(yradius);
            myEllipse.setYrectRadius(RectGeo.toRect(yradius));
            myEllipse.getEllipse().setStrokeWidth(PrimaryController.factor);
            myEllipse.getEllipse().setStroke(Color.BLUE);
            myEllipse.getEllipse().setFill(Color.GREEN);
            if (myEllipse.isGeo())
            {
                double[] xygeo = RectGeo.toGeo(RectGeo.toRect(x), RectGeo.toRect(y));
                myEllipse.setYgeoRadius(PrimaryController.toYkm(myEllipse.getYgeoCenter(), xygeo[1]));
                myEllipse.setSquare(myEllipse.getXgeoRadius() * myEllipse.getYgeoRadius() * Math.PI);
            }
            else
                myEllipse.setSquare(myEllipse.getXrectRadius() * myEllipse.getYrectRadius() * Math.PI);
            myEllipses.add(myEllipse);
            PrimaryController.group.getChildren().add(myEllipse.getEllipse());
            countPointsEllipse = 0;
        }
    }

    public static void redrawEllipse(boolean zoom)
    {
        if (myEllipses != null)
        {
            for (int i = 0; i < myEllipses.size(); i++)
                PrimaryController.group.getChildren().remove(myEllipses.get(i).getEllipse());
            for (int i = 0; i < myEllipses.size(); i++)
            {
                myEllipses.get(i).getEllipse().setCenterX(RectGeo.toRectForRedraw(myEllipses.get(i).getEllipse().getCenterX(), zoom));
                myEllipses.get(i).getEllipse().setCenterY(RectGeo.toRectForRedraw(myEllipses.get(i).getEllipse().getCenterY(), zoom));
                myEllipses.get(i).getEllipse().setRadiusX(RectGeo.toRectForRedraw(myEllipses.get(i).getEllipse().getRadiusX(), zoom));
                myEllipses.get(i).getEllipse().setRadiusY(RectGeo.toRectForRedraw(myEllipses.get(i).getEllipse().getRadiusY(), zoom));
                myEllipses.get(i).getEllipse().setStrokeWidth(PrimaryController.factor);

                PrimaryController.group.getChildren().add(myEllipses.get(i).getEllipse());
            }
        }
    }
}
