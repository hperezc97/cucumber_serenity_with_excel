package starter.stepdefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import starter.navigation.NavigateTo;
import starter.search.LookForInformation;
import starter.utils.ExcelReader;

public class SearchStepDefinitions {

    String excelFilePath =  "src/test/data/Login.xlsx";
    String a = "Sheet1";
    String b = "0";
    ExcelReader excelReader = new ExcelReader();

    @Given("{actor} is researching things on the internet")
    public void researchingThings(Actor actor) throws IOException, InvalidFormatException {
        actor.wasAbleTo(NavigateTo.theSearchHomePage());
        // Write code here that turns the phrase above into concrete actions
        List<Map<String, String>> testData = excelReader.getData(excelFilePath, a);

        // Obtener el índice de la fila
        int rowIndex = Integer.parseInt(b);
        // Verificar que el índice esté dentro de los límites de los datos
        if (rowIndex < 0 || rowIndex >= testData.size()) {
            throw new IndexOutOfBoundsException("El número de fila está fuera de los límites de los datos del Excel");
        }

        // Obtener las credenciales
        String username = testData.get(rowIndex).get("username");
        String password = testData.get(rowIndex).get("password");
        String datos = testData.get(rowIndex).get("datos");

        // Imprimir los valores de username y password
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    @When("{actor} looks up {string}")
    public void searchesFor(Actor actor, String term) {
        actor.attemptsTo(
                LookForInformation.about(term)
        );
    }

    @Then("{actor} should see information about {string}")
    public void should_see_information_about(Actor actor, String term) {
        actor.attemptsTo(
                Ensure.that(TheWebPage.title()).containsIgnoringCase(term)
        );
    }
}
