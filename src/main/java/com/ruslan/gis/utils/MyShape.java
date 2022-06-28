package com.ruslan.gis.utils;

public class MyShape
{
    private Integer id;
    private String shape;
    private Double length;
    private Double square;
    private Double perimeter;

    public MyShape()
    {
    }

    public MyShape(Integer id, String shape, Double length, Double square, Double perimeter)
    {
        this.id = id;
        this.shape = shape;
        this.length = length;
        this.square = square;
        this.perimeter = perimeter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getSquare() {
        return square;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }
}
