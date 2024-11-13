package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public record Triangle(Point a,Point b,Point c) implements PerimeterComputer {


    @JsonIgnore
    public boolean isEquilateral() {
        return (Math.abs(this.a.getDistance(b) - c.getDistance(b)) < 1e-5) && (Math.abs(a.getDistance(b) - a.getDistance(c)) < 1e-5) && (Math.abs(c.getDistance(b) - a.getDistance(c)) < 1e-5);
    }

    @JsonIgnore
    public double getPerimeterFromEquitaleralFormula() {
        return a.getDistance(b) * 3;
    }

    @Override
    @JsonIgnore
    public List<Point> points() {
        return List.of(a, b, c);
    }


}
