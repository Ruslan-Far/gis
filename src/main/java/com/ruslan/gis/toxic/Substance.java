package com.ruslan.gis.toxic;

public interface Substance
{
    double K4 = 1;
    double K5 = 1;
    double K8 = 0.081;
    double h = 0.05;
    double v = 5;

    double getK2();
    double getK3();
    double getK6();
    double getK7();
    double getQ();
    double getD();

    void setQ(double q);
    void setN(double n);
    void setTempAir(double tempAir);
    void setT(double t);
}
