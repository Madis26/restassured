import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class TestData {

    public Object getAuthoriseTokens() {
        String payload = "{\n" +
                "  \"username\": \"modular.system\",\n" +
                "  \"password\": \"pass\"\n" +
                "}";

        RestAssured.baseURI = "https://auth-api.sandbox.modularbank.xyz";
        Response response = given().urlEncodingEnabled(true)
                .contentType(ContentType.JSON)
                .header("x-tenant-code", "SANDBOX")
                .header("x-channel-code", "SYSTEM")
                .body(payload)
                .post("/api/v1/employees/authorise")
                .then().statusCode(200).extract().response();
        return response.jsonPath().getMap("data").get("token");
    }

    public Object createCustomer(String authToken, String guid, String randomPhoneNumber, String personName) {
        String timestamp = "1123421231243";
        String createCustomerPayload =
                "{\n"
                        + " \"activityCode\": \"ACTIVE\", \n"
                        + " \"birthDate\": \"1978-02-28\", \n"
                        + " \"email\": \"qa" + timestamp + "@modularbank.co\", \n"
                        + " \"id\": \"" + guid + "\", \n"
                        + " \"identificationNumber\": "
                        + " {\n"
                        + " \t\"idCountryCode\": \"FI\", \n"
                        + " \t\"idNumber\": \" " + randomPhoneNumber + "\" \n"
                        + "  }, \n"
                        + " \"name\": \"" + personName + "\", \n"
                        + " \"personTypeCode\": \"P\", \n"
                        + " \"sex\": \"M\", \n"
                        + " \"placeOfBirth\": \"Helsinki\", \n"
                        + " \"taxResidencyCountry\": \"FI\", \n"
                        + " \"buildingTypeCode\": \"APARTMENT\", \n"
                        + " \"businessAreaCode\": \"FINANCE\", \n"
                        + " \"acitivityCode\": \"EMPLOYEE\", \n"
                        + " \"dependantPersons\": 1, \n"
                        + " \"maritalStatusCode\": \"SINGLE\", \n"
                        + " \"nationality\": \"FI\", \n"
                        + " \"countryOfBirth\": \"FI\", \n"
                        + " \"language\": \"FI\", \n"
                        + " \"usResident\": false, \n"
                        + " \"pep\": false, \n"
                        + " \"educationCode\": \"HIGHER_EDUCATION\", \n"
                        + " \"employmentTimeCode\": \"UP_4_YEAR\", \n"
                        + " \"fixedEmploymentLength\": 5, \n"
                        + " \"phoneNumber\": \" " + randomPhoneNumber + " \", \n"
                        + " \"address\": {\n"
                        + " \t\"addressTypeCode\": \"R\", \n"
                        + " \t\"cityCounty\": \"Helsinki\", \n"
                        + " \t\"countryCode\": \"FI\", \n"
                        + " \t\"stateRegion\": \"Uusimaa\", \n"
                        + " \t\"street1\": \"Ratamestarinkatu 12\", \n"
                        + " \t\"zip\": \"30800101\",\n"
                        + " \t\"moveInDate\": \"2018-01-20\" \n"
                        + "  }, \n"
                        + " \"correspondenceAddress\": {\n"
                        + " \t\"addressTypeCode\": \"C\", \n"
                        + " \t\"cityCounty\": \"Helsinki\", \n"
                        + " \t\"countryCode\": \"FI\", \n"
                        + " \t\"stateRegion\": \"Uusimaa\", \n"
                        + " \t\"street1\": \"Ratamestarinkatu 12\", \n"
                        + " \t\"zip\": \"30800101\"\n"
                        + " \t}\n"
                        + "}";

        RestAssured.baseURI = "https://person-api.sandbox.modularbank.xyz";
        Response response = given().urlEncodingEnabled(true)
                .contentType(ContentType.JSON)
                .header("x-tenant-code", "SANDBOX")
                .header("x-channel-code", "SYSTEM")
                .header("x-auth-token", authToken)
                .body(createCustomerPayload)
                .post("/api/v1/persons")
                .then().statusCode(200).extract().response();

        return response.jsonPath().getMap("data").get("personId");

    }
}
