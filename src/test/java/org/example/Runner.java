package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = {
                "C:\\ProjetosBN\\Projetos individuais\\AulaApiVideo1\\RestAssuredvideo1\\src\\test\\java\\Feature\\.feature",
        },
        glue = {
                "org/example"
        },
        tags = "@CT004"

)

public class Runner {
}
