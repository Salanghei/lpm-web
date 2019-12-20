package com.hit.lpm.situation.model;

public class CourseNode {
    private String courseName;
    private int category;//0-已学习，1-即将学习，2-待学习，
    private int id;
    private String name;
    private double symbolSize;
    private double value;
    private int X;
    private int Y;

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setX(int x) {
        X = x;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(double symbolSize) {
        this.symbolSize = symbolSize;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
