package fr.pantheonsorbonne.ufr27.miage.camel;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TriangleRoutes extends RouteBuilder {

    @ConfigProperty(name = "quarkus.artemis.username")
    String userName;

    @Override
    public void configure() {
        from("file:data/triangles").unmarshal()
                .csv()
                .process(new CSVToTriangle())
                .process(new EquilateralTriangleProcessor())
                .choice()
                .when(header("isEquilateral").isEqualTo(true))
                .log("equi")
                .marshal().json()
                .to("sjms2:queue:M1.equilateral-" + userName)

                .when(header("isEquilateral").isEqualTo(false))
                .log("pas equi")
                .marshal().jacksonXml()
                .to("sjms2:queue:M1.autre-" + userName)

                .end();

        from("sjms2:queue:M1.equilateral-" + userName).unmarshal().json(Triangle.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        var tri = exchange.getMessage().getBody(Triangle.class);
                        exchange.getMessage().setBody(new Perimeter(tri.getPerimeterFromEquitaleralFormula()));
                    }
                }).marshal().json().to("file:data/perimeters/filename=equi.json");

        from("sjms2:queue:M1.autre-" + userName).unmarshal().jacksonXml(Triangle.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        var tri = exchange.getMessage().getBody(Triangle.class);
                        exchange.getMessage().setBody(new Perimeter(tri.perimeter()));
                    }
                }).marshal().json().to("file:data/perimeters?filename=notequi.json");


    }
}
