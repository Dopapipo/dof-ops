package fr.pantheonsorbonne.ufr27.miage.camel;

import jakarta.enterprise.context.ApplicationScoped;
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
                .process(new CsvToJsonProcessor())
                .process(new EquilateralTriangleProcessor())
                .choice()
                .when(header("isEquilateral").isEqualTo(true))
                .to("sjms2:queue:M1.equilateral-" + userName.split("@")[0])
                .when(header("isEquilateral").isEqualTo(false))
                .to("sjms2:queue:M1.autre-" + userName.split("@")[0])
                .end();

    }
}
