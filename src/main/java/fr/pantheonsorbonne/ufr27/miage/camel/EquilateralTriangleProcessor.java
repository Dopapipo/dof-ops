package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EquilateralTriangleProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = exchange.getIn().getBody(String.class);

        JsonNode jsonNode = mapper.readTree(json);

        Point a = mapper.treeToValue(jsonNode.get("a"), Point.class);
        Point b = mapper.treeToValue(jsonNode.get("b"), Point.class);
        Point c = mapper.treeToValue(jsonNode.get("c"), Point.class);

        exchange.getIn().setHeader("isEquilateral", isEquilateral(a, b, c));
    }

    private boolean isEquilateral(Point a, Point b, Point c) {
        double ab = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
        double bc = Math.sqrt(Math.pow(b.getX() - c.getX(), 2) + Math.pow(b.getY() - c.getY(), 2));
        double ac = Math.sqrt(Math.pow(a.getX() - c.getX(), 2) + Math.pow(a.getY() - c.getY(), 2));
        return ab == bc && bc == ac;
    }
}
