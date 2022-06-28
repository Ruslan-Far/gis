package com.ruslan.gis;

import com.ruslan.gis.drutils.DrMyZona;
import com.ruslan.gis.toxic.Ammiak;
import com.ruslan.gis.toxic.Chlor;
import com.ruslan.gis.toxic.Nitril;
import com.ruslan.gis.toxic.Substance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ZonaController
{
    public static Stage zonaStage;
    public static double xcenter;
    public static double ycenter;

    @FXML
    private TextField timeTextField;
    @FXML
    private TextField tempTextField;
    @FXML
    private TextField chlorTextField;
    @FXML
    private TextField ammiakTextField;
    @FXML
    private TextField nitrilTextField;
    @FXML
    private CheckBox chlorCheckBox;
    @FXML
    private CheckBox ammiakCheckBox;
    @FXML
    private CheckBox nitrilCheckBox;

    @FXML
    private void onClickOkBtn()
    {
        if (!(chlorCheckBox.isSelected() || ammiakCheckBox.isSelected() || nitrilCheckBox.isSelected()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберите хотя бы один СДЯВ");
            alert.showAndWait();
        }
        else
        {
            double N = Double.parseDouble(timeTextField.getText());
            ArrayList<Substance> substances = new ArrayList<>();

            if (chlorCheckBox.isSelected())
            {
                Substance substance = new Chlor();
                substance.setQ(Double.parseDouble(chlorTextField.getText()));
                substances.add(substance);
            }
            if (ammiakCheckBox.isSelected())
            {
                Substance substance = new Ammiak();
                substance.setQ(Double.parseDouble(ammiakTextField.getText()));
                substances.add(substance);
            }
            if (nitrilCheckBox.isSelected())
            {
                Substance substance = new Nitril();
                substance.setQ(Double.parseDouble(nitrilTextField.getText()));
                substances.add(substance);
            }

            for (int i = 0; i < substances.size(); i++)
            {
                substances.get(i).setN(N);
                substances.get(i).setTempAir(Double.parseDouble(tempTextField.getText()));
                substances.get(i).setT(calcT(substances.get(i)));
            }

            double Qe = calcQe(substances);
            double G = calcG(Qe);
            double Gp = calcGp(N);
            if (Gp < G)
                G = Gp;
            double Sf = calcSf(G, N);
            DrMyZona.drawZona(G, Sf);
            zonaStage.close();
        }
    }

    @FXML
    private void onClickCancelBtn()
    {
        zonaStage.close();
    }

    private double calcT(Substance substance)
    {
        double T = (Substance.h * substance.getD()) / (substance.getK2() * Substance.K4 * substance.getK7());
        return T;
    }

    private double calcQe(ArrayList<Substance> substances)
    {
        double Qe = 0;

        for (Substance substance : substances)
        {
            Qe += substance.getK2() * substance.getK3() * substance.getK6() * substance.getK7() * substance.getQ() / substance.getD();
        }
        Qe *= 20 * Substance.K4 * Substance.K5;
        return Qe;
    }

    private double calcG(double Qe)
    {
        double G;

        if (Qe < 0.01)
            G = 0.38;
        else if (Qe >= 0.01 && Qe <= 0.05)
            G = interpolate(0.38, 0.85, 0.01, 0.05, Qe);
        else if (Qe >= 0.05 && Qe <= 0.1)
            G = interpolate(0.85, 1.25, 0.05, 0.1, Qe);
        else if (Qe >= 0.1 && Qe <= 0.5)
            G = interpolate(1.25, 3.16, 0.1, 0.5, Qe);
        else if (Qe >= 0.5 && Qe <= 1)
            G = interpolate(3.16, 4.75, 0.5, 1, Qe);
        else if (Qe >= 1 && Qe <= 3)
            G = interpolate(4.75, 9.18, 1, 3, Qe);
        else if (Qe >= 3 && Qe <= 5)
            G = interpolate(9.18, 12.53, 3, 5, Qe);
        else if (Qe >= 5 && Qe <= 10)
            G = interpolate(12.53, 19.20, 5, 10, Qe);
        else if (Qe >= 10 && Qe <= 20)
            G = interpolate(19.20, 29.56, 10, 20, Qe);
        else if (Qe >= 20 && Qe <= 30)
            G = interpolate(29.56, 38.13, 20, 30, Qe);
        else if (Qe >= 30 && Qe <= 50)
            G = interpolate(38.13, 52.67, 30, 50, Qe);
        else if (Qe >= 50 && Qe <= 70)
            G = interpolate(52.67, 65.23, 50, 70, Qe);
        else if (Qe >= 70 && Qe <= 100)
            G = interpolate(65.23, 81.91, 70, 100, Qe);
        else if (Qe >= 100 && Qe <= 300)
            G = interpolate(81.91, 166, 100, 300, Qe);
        else if (Qe >= 300 && Qe <= 500)
            G = interpolate(166, 231, 300, 500, Qe);
        else if (Qe >= 500 && Qe <= 700)
            G = interpolate(231, 288, 500, 700, Qe);
        else if (Qe >= 700 && Qe <= 1000)
            G = interpolate(288, 363, 700, 1000, Qe);
        else if (Qe >= 1000 && Qe <= 2000)
            G = interpolate(363, 572, 1000, 2000, Qe);
        else
            G = 572;
        return G;
    }

    private double interpolate(double aG, double bG, double aQe, double bQe, double Qe)
    {
        return aG + ((bG - aG) / (bQe - aQe) * (Qe - aQe));
    }

    private double calcGp(double N)
    {
        double Gp = N * Substance.v;
        return Gp;
    }

    private double calcSf(double G, double N)
    {
        double Sf = Substance.K8 * Math.pow(G, 2) * Math.pow(N, 0.2);
        return Sf;
    }
}
