package com.ruslan.gis.drutils;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.utils.RectGeo;
import com.ruslan.gis.shapes.MyRectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class DrMyRectangle
{
    public static ArrayList<MyRectangle> myRectangles;
    public static int countPointsRectangle;

    private static MyRectangle myRectangle;

    public static void drawRectangle(double x, double y)
    {
        if (countPointsRectangle == 1)
        {
            myRectangle = new MyRectangle(new Rectangle());
            myRectangle.getRectangle().setX(x);
            myRectangle.getRectangle().setY(y);
            myRectangle.setXrectLeftTop(RectGeo.toRect(x));
            myRectangle.setYrectLeftTop(RectGeo.toRect(y));
            if (!(PrimaryController.myPoints == null || PrimaryController.myPoints.size() == 0))
            {
                double[] xygeo = RectGeo.toGeo(myRectangle.getXrectLeftTop(), myRectangle.getYrectLeftTop());
                myRectangle.setXgeoLeftTop(xygeo[0]);
                myRectangle.setYgeoLeftTop(xygeo[1]);
                myRectangle.setGeo(true);
            }
            countPointsRectangle += 1;
        }
        else
        {
            double width = Math.abs(x - myRectangle.getRectangle().getX());
            double height = Math.abs(y - myRectangle.getRectangle().getY());
            myRectangle.getRectangle().setWidth(width);
            myRectangle.getRectangle().setHeight(height);
            myRectangle.setRectWidth(RectGeo.toRect(width));
            myRectangle.setRectHeight(RectGeo.toRect(height));
            if (myRectangle.isGeo())
            {
                double[] xygeo = RectGeo.toGeo(RectGeo.toRect(x), RectGeo.toRect(y));
                myRectangle.setGeoWidth(PrimaryController.toXkm(myRectangle.getXgeoLeftTop(), xygeo[0]));
                myRectangle.setGeoHeight(PrimaryController.toYkm(myRectangle.getYgeoLeftTop(), xygeo[1]));
                myRectangle.setSquare(myRectangle.getGeoWidth() * myRectangle.getGeoHeight());
                myRectangle.setPerimeter(myRectangle.getGeoWidth() * 2 + myRectangle.getGeoHeight() * 2);
            }
            else
            {
                myRectangle.setSquare(myRectangle.getRectWidth() * myRectangle.getRectHeight());
                myRectangle.setPerimeter(myRectangle.getRectWidth() * 2 + myRectangle.getRectHeight() * 2);
            }
            myRectangle.getRectangle().setStrokeWidth(PrimaryController.factor);
            myRectangle.getRectangle().setStroke(Color.RED);
            myRectangle.getRectangle().setFill(Color.YELLOW);
            myRectangles.add(myRectangle);
            PrimaryController.group.getChildren().add(myRectangle.getRectangle());
            countPointsRectangle = 0;
        }
    }

    public static void redrawRectangle(boolean zoom)
    {
        if (myRectangles != null)
        {
            for (int i = 0; i < myRectangles.size(); i++)
                PrimaryController.group.getChildren().remove(myRectangles.get(i).getRectangle());
            for (int i = 0; i < myRectangles.size(); i++)
            {
                myRectangles.get(i).getRectangle().setX(RectGeo.toRectForRedraw(myRectangles.get(i).getRectangle().getX(), zoom));
                myRectangles.get(i).getRectangle().setY(RectGeo.toRectForRedraw(myRectangles.get(i).getRectangle().getY(), zoom));
                myRectangles.get(i).getRectangle().setWidth(RectGeo.toRectForRedraw(myRectangles.get(i).getRectangle().getWidth(), zoom));
                myRectangles.get(i).getRectangle().setHeight(RectGeo.toRectForRedraw(myRectangles.get(i).getRectangle().getHeight(), zoom));
                myRectangles.get(i).getRectangle().setStrokeWidth(PrimaryController.factor);

                PrimaryController.group.getChildren().add(myRectangles.get(i).getRectangle());
            }
        }
    }
}
