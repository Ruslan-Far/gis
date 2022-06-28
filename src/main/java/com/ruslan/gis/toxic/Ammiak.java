package com.ruslan.gis.toxic;

public class Ammiak implements Substance
{
    private final double K2 = 0.025;
    private final double K3 = 0.04;
    private final double d = 0.681;

    private double N;
    private double T;
    private double tempAir;
    private double K6;
    private double K7;
    private double Q;

    public double getK2()
    {
        return K2;
    }

    public double getK3()
    {
        return K3;
    }

    public double getK6()
    {
        return K6;
    }

    public void setK6(double k6)
    {
        K6 = k6;
    }

    public double getK7()
    {
        return K7;
    }

    public void setK7(double k7)
    {
        K7 = k7;
    }

    public double getQ()
    {
        return Q;
    }

    public void setQ(double q)
    {
        Q = q;
    }

    public double getD()
    {
        return d;
    }

    public double getN()
    {
        return N;
    }

    public void setN(double n)
    {
        N = n;
    }

    public double getTempAir()
    {
        return tempAir;
    }

    public void setTempAir(double tempAir)
    {
        this.tempAir = tempAir;
        if (tempAir < -40)
            setK7(0.9);
        else if (tempAir >= -40 && tempAir <= -20)
            setK7(0.9 + ((1 - 0.9) / ((-20) - (-40))) * (tempAir - (-40)));
        else
            setK7(1);
    }

    public double getT()
    {
        return T;
    }

    public void setT(double t)
    {
        T = t;
        if (T < 1)
            setK6(1);
        else if (N < T)
            setK6(Math.pow(N, 0.8));
        else
            setK6(Math.pow(T, 0.8));
    }
}
