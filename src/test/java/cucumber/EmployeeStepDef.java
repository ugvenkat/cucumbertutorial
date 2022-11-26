package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class EmployeeStepDef {
    private static final String BASE_URL = "http://localhost:57888/api/Employees";
    private static Response response;
    private static String jsonString;

    @Given("A list of employees are available")
    public void listOfEmployeesAreAvailable() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        /*
        response = request.get("/GetEmployeeById/15");
        jsonString = response.asString();
        JSONObject jsonObject = new JSONObject(jsonString);
        int employeeId = jsonObject.getInt("EmployeeId");
        Assert.assertEquals(15, employeeId);
         */
        response = request.get("/GetAllEmployees");
        jsonString = response.asString();
        List<Map<String, String>> ArrayOfEmployee = JsonPath.from(jsonString).get("ArrayOfEmployee");
        Assert.assertTrue(ArrayOfEmployee.size() > 0);
    }

    @When("I add a employee")
    public void addEmployeeInList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        String AddJason = "{\"EmployeeId\":28,\"Name\":\"Venkatesh\",\"ManagerId\":6}";
        response = request.body(AddJason).post("/AddEmployee");
    }

    @Then("The employee is added")
    public void employeeIsAdded() {
        Assert.assertEquals(204, response.getStatusCode());
    }

    @When("I remove a employee")
    public void removeBookFromList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.delete("/DeleteEmployee/28");
        Assert.assertEquals(204, response.getStatusCode());
    }

    @Then("The employee is removed")
    public void employeeIsRemoved() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get("/GetEmployeeById/28");
        Assert.assertEquals(200, response.getStatusCode());
        jsonString = response.asString();
        Assert.assertEquals("null", jsonString);
    }
}
