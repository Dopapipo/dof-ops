package fr.pantheonsorbonne.ufr27.miage.camel;

public record Point(double x, double y) {
    public double getDistance(Point point) {
        return Math.sqrt(Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2));
    }

}
