import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class CreateTransactionsTest {

    // TODO Make automated data generator for guid, randomPhoneNumber, timeStamp and fullName.
    // TODO swagger json data fields tests. Needs more data generator and class for json modification to tests allowed and not allowed symbols, lengths.

    String timestamp = "1123421231243";
    String authToken = new TestData().getAuthoriseTokens().toString();
    String createTrasactionPayload =
            "{\n"
            + " \"details\": \"Card topup\",\n"
            + " \"effectiveDate\": \"2020-06-08\",\n"
            + " \"money\": {\n"
            + " \t\"amount\": 238,\n"
            + " \t\"currencyCode\": \"EUR\"\n"
            + " },\n"
            + " \"source\": {\n"
            + " \t\"sourceName\": \"CARD_TOPUP\",\n"
            + " \t\"sourceRef\": \"ID-"+timestamp+"\"\n"
            + " },\n"
            + " \"transactionTypeCode\": \"CARD_TOPUP\"\n"
            + "}";

    /*
    @BeforeClass
    public void setData() throws IOException, JSONException {
        personId = new Helper().createCustomer().toString();
    }
    */

    @Test
    public void createTransactionRespondsOk() {
        String accountId = "1213231231";

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
            .body(createTrasactionPayload)
        .when()
            .post("/api/v1/accounts/"+accountId+"/transactions")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountTransactionId", notNullValue());
    }

    @Test
    public void createTransactionWhenAccountIdUnknown() {
        String accountId = "1213231231";

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
            .body(createTrasactionPayload)
        .when()
            .post("/api/v1/accounts/"+accountId+"/transaction")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("error", notNullValue());
    }
}
