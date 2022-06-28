package com.ruslan.gis;

import com.ruslan.gis.drutils.*;
import com.ruslan.gis.utils.MyPoint;
import com.ruslan.gis.utils.RectGeo;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class PrimaryController
{
    public static Stage primaryStage;
    public static final double FACTOR = 1.13;
    public static ObservableList<MyPoint> myPoints;

    public static double km_per_degree_longitude;
    public static final double KM_PER_DEGREE_LATITUDE = 111.3;

    public static Group group;

    private Image image;

    public static double widthImg;
    public static double heightImg;
    public static double factor = 1.0;

    @FXML
    private ImageView mapImageView;
    @FXML
    private Group mainGroup;

    @FXML
    private void onClickMenuFileItemOpen() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null)
        {
            DrMyLine.myLines = new ArrayList<>();
            DrMyEllipse.myEllipses = new ArrayList<>();
            DrMyRectangle.myRectangles = new ArrayList<>();
            DrMyPolygon.myPolygons = new ArrayList<>();
            DrMyZona.myZonas = new ArrayList<>();

            image = new Image(file.toURI().toURL().toString());
            widthImg = image.getWidth();
            heightImg = image.getHeight();
            group = new Group();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ГИС");
            alert.setHeaderText("Зарегистрировать изображение?");
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes)
            {
                RegistrationImageController.file = file;
                startRegistrationImageStage();
                if (!(myPoints == null || myPoints.size() == 0))
                {
                    double midLatitude =
                        myPoints.get(3).getYgeo()
                        +
                        ((myPoints.get(0).getYgeo() - myPoints.get(3).getYgeo()) / 2);
                    km_per_degree_longitude = KM_PER_DEGREE_LATITUDE * Math.cos(midLatitude / 180 * Math.PI);
                }
            }
            mapImageView.setImage(image);
            mainGroup.getChildren().add(group);
            EventHandler<MouseEvent> eventHandler = new ImplEventHandlerMouse();
            mapImageView.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        }
    }

    @FXML
    private void onClickMenuFileItemExit()
    {
        if (AddingBreakpointController.addingBreakpointStage != null)
            AddingBreakpointController.addingBreakpointStage.close();
        if (AddingShapeController.addingShapeStage != null)
            AddingShapeController.addingShapeStage.close();
        if (EditingShapeController.editingShapeStage != null)
            EditingShapeController.editingShapeStage.close();
        if (RegistrationImageController.registrationImageStage != null)
            RegistrationImageController.registrationImageStage.close();
        if (SqlController.sqlStage != null)
            SqlController.sqlStage.close();
        if (TableController.tableStage != null)
            TableController.tableStage.close();
        if (ZonaController.zonaStage != null)
            ZonaController.zonaStage.close();
        primaryStage.close();
    }

    @FXML
    private void onClickMenuDataItemTable() throws IOException
    {
        startTableStage();
    }

    @FXML
    private void onClickMenuDataItemSql() throws IOException
    {
        startSqlStage();
    }

    @FXML
    private void onClickZoomInBtn()
    {
        widthImg *= FACTOR;
        heightImg *= FACTOR;
        mapImageView.setFitWidth(widthImg);
        mapImageView.setFitHeight(heightImg);

        factor *= FACTOR;

        redraw(true);
    }

    @FXML
    private void onClickZoomOutBtn()
    {
        widthImg /= FACTOR;
        heightImg /= FACTOR;
        mapImageView.setFitWidth(widthImg);
        mapImageView.setFitHeight(heightImg);

        factor /= FACTOR;

        redraw(false);
    }

    @FXML
    private void onClickLineBtn()
    {
        DrMyLine.countPointsLine += 1;
    }

    @FXML
    private void onClickEllipseBtn()
    {
        DrMyEllipse.countPointsEllipse += 1;
    }

    @FXML
    private void onClickRectangleBtn()
    {
        DrMyRectangle.countPointsRectangle += 1;
    }

    @FXML
    private void onClickPolygonBtn()
    {
        DrMyPolygon.countPointsPolygon += 1;
    }

    @FXML
    private void onClickZonaBtn()
    {
        DrMyZona.countPointsZona += 1;
    }

    private void startRegistrationImageStage() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registration_image_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Регистрация изображения");
        stage.setScene(scene);

        RegistrationImageController.registrationImageStage = stage;

        stage.showAndWait();
    }

    private void startTableStage() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Таблица");
        stage.setScene(scene);

        TableController.tableStage = stage;

        stage.showAndWait();
    }

    private void startSqlStage() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sql_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("SQL");
        stage.setScene(scene);

        SqlController.sqlStage = stage;

        stage.showAndWait();
    }

    private void startZonaStage(double x, double y) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("zona_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Создание зоны заражения");
        stage.setScene(scene);

        ZonaController.zonaStage = stage;
        ZonaController.xcenter = x;
        ZonaController.ycenter= y;

        stage.showAndWait();
    }

    private class ImplEventHandlerMouse implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            if (DrMyLine.countPointsLine != 0)
                DrMyLine.drawLine(x, y);
            else if (DrMyEllipse.countPointsEllipse != 0)
                DrMyEllipse.drawEllipse(x, y);
            else if (DrMyRectangle.countPointsRectangle != 0)
                DrMyRectangle.drawRectangle(x, y);
            else if (DrMyPolygon.countPointsPolygon != 0)
            {
                DrMyPolygon.drawLineForPolygon(x, y);
                if (DrMyPolygon.countPointsPolygon == 0)
                {
                    if (Math.abs(DrMyPolygon.myPolygon
                            .getMyLines().get(0).getLine().getStartX() - x) <= 2
                            && Math.abs(DrMyPolygon.myPolygon
                            .getMyLines().get(0).getLine().getStartY() - y) <= 2)
                    {
                        DrMyPolygon.drawPolygon();
                        DrMyPolygon.countPointsPolygon = 0;
                    }
                    else
                    {
                        DrMyPolygon.countPointsPolygon += 2;
                        DrMyPolygon.drawLineForPolygon(x, y);
                    }
                }
            }
            else if (DrMyZona.countPointsZona != 0) {
                try {
                    startZonaStage(x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                RectGeo.determineCoordsOfPoint(mouseEvent);
        }
    }

    private void redraw(boolean zoom)
    {
        DrMyLine.redrawLine(zoom);
        DrMyEllipse.redrawEllipse(zoom);
        DrMyRectangle.redrawRectangle(zoom);
        DrMyPolygon.redrawPolygon(zoom);
        DrMyZona.redrawZona(zoom);
    }

    public static double toXkm(double x1, double x2)
    {
        return Math.abs(x2 - x1) * km_per_degree_longitude;
    }

    public static double toYkm(double y1, double y2)
    {
        return Math.abs(y2 - y1) * KM_PER_DEGREE_LATITUDE;
    }

    public static double fromXkm(double xgeoRadius)
    {
        return xgeoRadius / km_per_degree_longitude;
    }

    public static double fromYkm(double ygeoRadius)
    {
        return ygeoRadius / KM_PER_DEGREE_LATITUDE;
    }
}