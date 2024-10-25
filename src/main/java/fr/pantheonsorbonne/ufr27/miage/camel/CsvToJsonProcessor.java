package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CsvToJsonProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<List<String>> dataString = exchange.getIn().getBody(List.class);

        if (dataString.size() != 3) {
            throw new IllegalArgumentException("Triangles are made of 3 points");
        }
        Point a = parsePoint(dataString.get(0));
        Point b = parsePoint(dataString.get(1));
        Point c = parsePoint(dataString.get(2));



        Triangle triangle = new Triangle(a, b, c);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(triangle);

        exchange.getIn().setBody(json);
    }
    private Point parsePoint(List<String> coordinates) {
        if (coordinates.size() != 2) {
            throw new IllegalArgumentException("Each point must contain exactly two coordinates.");
        }

        double x = Double.parseDouble(coordinates.get(0).trim());
        double y = Double.parseDouble(coordinates.get(1).trim());

        return new Point(x, y);
    }
}


