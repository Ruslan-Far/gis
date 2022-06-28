package com.ruslan.gis.drutils;

import com.ruslan.gis.PrimaryController;
import com.ruslan.gis.utils.RectGeo;
import com.ruslan.gis.shapes.MyLine;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class DrMyLine
{
    public static ArrayList<MyLine> myLines;
    public static int countPointsLine;

    private static MyLine myLine;

    public static void drawLine(double x, double y)
    {
        if (countPointsLine == 1)
        {
            myLine = new MyLine(new Line());
            myLine.getLine().setStartX(x);
            myLine.getLine().setStartY(y);
            myLine.setX1rect(RectGeo.toRect(x));
            myLine.setY1rect(RectGeo.toRect(y));
            if (!(PrimaryController.myPoints == null || PrimaryController.myPoints.size() == 0))
            {
                double[] xygeo = RectGeo.toGeo(myLine.getX1rect(), myLine.getY1rect());
                myLine.setX1geo(xygeo[0]);
                myLine.setY1geo(xygeo[1]);
                myLine.setGeo(true);
            }
            countPointsLine += 1;
        }
        else
        {
            myLine.getLine().setEndX(x);
            myLine.getLine().setEndY(y);
            myLine.setX2rect(RectGeo.toRect(x));
            myLine.setY2rect(RectGeo.toRect(y));
            if (myLine.isGeo())
            {
                double[] xygeo = RectGeo.toGeo(myLine.getX2rect(), myLine.getY2rect());
                myLine.setX2geo(xygeo[0]);
                myLine.setY2geo(xygeo[1]);
                myLine.setLength
                (
                    Math.sqrt
                    (
                        Math.pow(PrimaryController.toXkm(myLine.getX1geo(), myLine.getX2geo()), 2)
                        +
                        Math.pow(PrimaryController.toYkm(myLine.getY1geo(), myLine.getY2geo()), 2)
                    )
                );
            }
            else
            {
                myLine.setLength
                (
                    Math.sqrt
                    (
                        Math.pow(Math.abs(myLine.getX2rect() - myLine.getX1rect()), 2)
                        +
                        Math.pow(Math.abs(myLine.getY2rect() - myLine.getY1rect()), 2)
                    )
                );
            }
            myLine.getLine().setStrokeWidth(PrimaryController.factor);
            myLine.getLine().setStroke(Color.ORANGE);
            myLines.add(myLine);
            PrimaryController.group.getChildren().add(myLine.getLine());
            countPointsLine = 0;
        }
    }

    public static void redrawLine(boolean zoom)
    {
        if (myLines != null)
        {
            for (int i = 0; i < myLines.size(); i++)
                PrimaryController.group.getChildren().remove(myLines.get(i).getLine());
            for (int i = 0; i < myLines.size(); i++)
            {
                myLines.get(i).getLine().setStartX(RectGeo.toRectForRedraw(myLines.get(i).getLine().getStartX(), zoom));
                myLines.get(i).getLine().setStartY(RectGeo.toRectForRedraw(myLines.get(i).getLine().getStartY(), zoom));
                myLines.get(i).getLine().setEndX(RectGeo.toRectForRedraw(myLines.get(i).getLine().getEndX(), zoom));
                myLines.get(i).getLine().setEndY(RectGeo.toRectForRedraw(myLines.get(i).getLine().getEndY(), zoom));
                myLines.get(i).getLine().setStrokeWidth(PrimaryController.factor);

                PrimaryController.group.getChildren().add(myLines.get(i).getLine());
            }
        }
    }
}
