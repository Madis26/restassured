import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class CreateAccountTest {
    // TODO Make automated data generator for guid, randomPhoneNumber, timeStamp and fullName.
    // TODO swagger json data fields tests. Needs more data generator and class for json modification to tests allowed and not allowed symbols, lengths.

    String guid = "11213123123123";
    String randomPhoneNumber = "1123421231243";
    String timestamp = "1123421231243";
    String personName = "1123421231243";

    String authToken = new TestData().getAuthoriseTokens().toString();
    String personId = new TestData().createCustomer(authToken, guid, randomPhoneNumber, personName).toString();
    private String createAccountPayload =
        "{\n"
        + " \"accountName\": \"Demo account\", \n"
        + " \"accountTypeCode\": \"CURRENCY\", \n"
        + " \"currencyCode\": \"EUR\", \n"
        + " \"customerGroupCode\": \"GROUP_A\", \n"
        + " \"personId\": \"" + personId + ", \n"
        + " \"personName\":\"" + personName + ", \n"
        + " \"priceListTypeCode\": \"STANDARD\", \n"
        + " \"residencyCountryCode\": \"FI\", \n"
        + " \"source\": { \n"
        + " \t\"sourceName\": \"TEST\", \n"
        + " \t\"sourceRef\": \"" + guid + " \n"
        + "  }\n"
        + "}";

/*
    @BeforeClass
    public void setData() throws IOException, JSONException {
        personId = new Helper().createCustomer().toString();
    }
*/

    @Test
    public void createAccountRespondOk() {
        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
            .body(createAccountPayload)
        .when()
            .post("/api/v1/persons/"+ personId +"/accounts")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", notNullValue());
    }

    @Test
    public void createAccountPersonIdNotExists() {
        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
            .body(createAccountPayload)
        .when()
            .post("/api/v1/persons/"+ personId + "1" +"/accounts")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", notNullValue());
    }

    @Test
    public void createAccount_invalid_auth_token() {
        String fakeAuthToken = "eföldsjgsfdölfdsgösdlkgj";
        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", fakeAuthToken)
            .body(createAccountPayload)
        .when()
            .post("/api/v1/persons/"+ personId + "1" +"/accounts")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("accountId", notNullValue());
    }

    @Test
    public void createAccount_correct_other_users_auth_token() {
        String new_authToken = new TestData().getAuthoriseTokens().toString();

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given().urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", new_authToken)
            .body(createAccountPayload)
        .when()
            .post("/api/v1/persons/"+ personId + "1" +"/accounts")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", notNullValue());
    }
}


