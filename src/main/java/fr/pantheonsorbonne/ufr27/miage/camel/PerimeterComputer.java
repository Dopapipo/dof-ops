package fr.pantheonsorbonne.ufr27.miage.camel;

import java.util.List;

public interface PerimeterComputer {
    public abstract List<Point> points();

    default double perimeter() {
        double perimeter = 0;
        Point currPoint = points().getLast();
        for (Point point : points()) {
            perimeter += currPoint.getDistance(point);
        }
        return perimeter;
    }
}
