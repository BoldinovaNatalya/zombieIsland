package ru.vsu.cs.zombie.server.logic;

public class Point {

    private int x;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public double distance(Point point) {
        return distance(point.x, point.y);
    }

    public static double distance(Point p1, Point p2) {
        return p1.distance(p2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point tmp = (Point)obj;
            return x == tmp.x & y == tmp.y;
        }
        return false;
    }
}
