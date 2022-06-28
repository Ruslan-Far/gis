package com.ruslan.gis;

import com.ruslan.gis.utils.MyPoint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class RegistrationImageController
{
    public static Stage registrationImageStage;
    public static File file;
    public static MyPoint myPoint;

    private MyPoint selectedMyPoint;
    private Image image;
    private double widthImg;
    private double heightImg;
    private double factor = 1.0;

    @FXML
    private ImageView scaleMapImageView;
    @FXML
    private TableView<MyPoint> pointsTableView;

    public void initialize() throws MalformedURLException
    {
        initImage();
        PrimaryController.myPoints = FXCollections.observableArrayList();
        initTableColumns();
        pointsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyPoint>()
        {
            @Override
            public void changed(ObservableValue<? extends MyPoint> observableValue, MyPoint oldPoint, MyPoint newPoint)
            {
                selectedMyPoint = newPoint;
            }
        });
        EventHandler<MouseEvent> eventHandler = new ImplEventHandlerMouse();
        scaleMapImageView.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @FXML
    private void onClickDeleteBtn()
    {
        if (selectedMyPoint != null)
        {
            PrimaryController.myPoints.remove(selectedMyPoint);
            pointsTableView.setItems(PrimaryController.myPoints);
        }
    }

    @FXML
    private void onClickCancelBtn()
    {
        PrimaryController.myPoints = null;
        registrationImageStage.close();
    }

    @FXML
    private void onClickOkBtn()
    {
        if (PrimaryController.myPoints.size() != 0)
        {
            MyPoint.factorToGeoXTop =
                    (PrimaryController.myPoints.get(1).getXgeo() - PrimaryController.myPoints.get(0).getXgeo())
                    /
                    (PrimaryController.myPoints.get(1).getXrect() - PrimaryController.myPoints.get(0).getXrect());

            MyPoint.factorToGeoXBottom =
                    (PrimaryController.myPoints.get(2).getXgeo() - PrimaryController.myPoints.get(3).getXgeo())
                    /
                    (PrimaryController.myPoints.get(2).getXrect() - PrimaryController.myPoints.get(3).getXrect());

            MyPoint.factorToGeoYLeft =
                    (PrimaryController.myPoints.get(0).getYgeo() - PrimaryController.myPoints.get(3).getYgeo())
                    /
                    (PrimaryController.myPoints.get(3).getYrect() - PrimaryController.myPoints.get(0).getYrect());

            MyPoint.factorToGeoYRight =
                    (PrimaryController.myPoints.get(1).getYgeo() - PrimaryController.myPoints.get(2).getYgeo())
                    /
                    (PrimaryController.myPoints.get(2).getYrect() - PrimaryController.myPoints.get(1).getYrect());
        }
        registrationImageStage.close();
    }

    @FXML
    private void onClickZoomInBtn()
    {
        widthImg *= PrimaryController.FACTOR;
        heightImg *= PrimaryController.FACTOR;
        scaleMapImageView.setFitWidth(widthImg);
        scaleMapImageView.setFitHeight(heightImg);

        factor *= PrimaryController.FACTOR;
    }

    @FXML
    private void onClickZoomOutBtn()
    {
        widthImg /= PrimaryController.FACTOR;
        heightImg /= PrimaryController.FACTOR;
        scaleMapImageView.setFitWidth(widthImg);
        scaleMapImageView.setFitHeight(heightImg);

        factor /= PrimaryController.FACTOR;
    }

    private void initImage() throws MalformedURLException
    {
        image = new Image(file.toURI().toURL().toString());
        widthImg = image.getWidth();
        heightImg = image.getHeight();
        scaleMapImageView.setImage(image);
    }

    private void initTableColumns()
    {
        TableColumn<MyPoint, String> nameColumn = new TableColumn<>("Точка");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsTableView.getColumns().add(nameColumn);

        TableColumn<MyPoint, Double> xColumn = new TableColumn<>("Коорд X");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("xgeo"));
        pointsTableView.getColumns().add(xColumn);

        TableColumn<MyPoint, Double> yColumn = new TableColumn<>("Коорд Y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("ygeo"));
        pointsTableView.getColumns().add(yColumn);
    }

    private class ImplEventHandlerMouse implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            myPoint = new MyPoint("Точка", 0.0, 0.0,
                    (int) Math.round(mouseEvent.getX() / factor), (int) Math.round(mouseEvent.getY() / factor));
            startAddingBreakpointStage();
            if (myPoint != null)
            {
                PrimaryController.myPoints.add(myPoint);
                pointsTableView.setItems(PrimaryController.myPoints);
                myPoint = null;
            }
        }

        private void startAddingBreakpointStage()
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adding_breakpoint_view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Добавить контрольную точку");
            stage.setScene(scene);

            AddingBreakpointController.addingBreakpointStage = stage;

            stage.showAndWait();
        }
    }
}

