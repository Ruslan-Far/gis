package com.ruslan.gis.utils;

public class MyPoint
{
    private String name;
    private Double xgeo;
    private Double ygeo;
    private Integer xrect;
    private Integer yrect;
    public static Double factorToGeoXTop;
    public static Double factorToGeoXBottom;
    public static Double factorToGeoYLeft;
    public static Double factorToGeoYRight;

    public MyPoint(String name, Double xgeo, Double ygeo, Integer xrect, Integer yrect)
    {
        this.name = name;
        this.xgeo = xgeo;
        this.ygeo = ygeo;
        this.xrect = xrect;
        this.yrect = yrect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getXgeo() {
        return xgeo;
    }

    public void setXgeo(Double xgeo) {
        this.xgeo = xgeo;
    }

    public Double getYgeo() {
        return ygeo;
    }

    public void setYgeo(Double ygeo) {
        this.ygeo = ygeo;
    }

    public Integer getXrect() {
        return xrect;
    }

    public void setXrect(Integer xrect) {
        this.xrect = xrect;
    }

    public Integer getYrect() {
        return yrect;
    }

    public void setYrect(Integer yrect) {
        this.yrect = yrect;
    }
}
