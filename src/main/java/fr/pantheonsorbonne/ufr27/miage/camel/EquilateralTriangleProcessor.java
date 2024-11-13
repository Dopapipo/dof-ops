package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EquilateralTriangleProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Triangle trianble = exchange.getIn().getBody(Triangle.class);


        exchange.getIn().setHeader("isEquilateral", trianble.isEquilateral());
    }


}
