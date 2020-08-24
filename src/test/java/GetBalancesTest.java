import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetBalancesTest {

    // TODO Make automated data generator for guid, randomPhoneNumber, timeStamp and fullName.
    // TODO swagger json data fields tests. Needs more data generator and class for json modification to tests allowed and not allowed symbols, lengths.

    String authToken = new TestData().getAuthoriseTokens().toString();

    /*
    @BeforeClass
    public void setData() throws IOException, JSONException {
        personId = new Helper().createCustomer().toString();
    }
    */

    @Test
    public void getBalancesRespondsOk() {
        String accountId = "123123123";

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
        .when()
            .post("/api/v1/accounts/" + accountId + "/balances")
        .then()
            .statusCode(200);
    }

    @Test
    public void getBalancesAccountIdUnknown() {
        String accountId = "123123123";

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
        .when()
            .post("/api/v1/accounts/" + accountId + "/balances")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("data.personId", notNullValue());
    }
}
