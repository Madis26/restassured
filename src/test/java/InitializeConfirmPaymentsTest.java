import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class InitializeConfirmPaymentsTest {
    String timestamp = "12312312321";
    String authToken = new TestData().getAuthoriseTokens().toString();
    String accountId = "1123123213";
    String initializeConfirmPaymentPayload =
        "{ \n"
        + " \"counterparty\": { \n"
        + " \t\"counterpartyTypeCode\": \"IBAN\", \n"
        + " \t\"name\": \"Ben Ficher\", \n"
        + " \t\"value\": \"EE459999000000010140\" \n"
        + " }, \n"
        + " \"details\": \"Details\", \n"
        + " \"directionCode\": \"OUT\", \n"
        + " \"effectiveDate\": \"2020-06-08\", \n"
        + " \"endToEndId\": \"NOTPROVIDED\", \n"
        + " \"money\": { \n"
        + " \t\"amount\": 24.35, \n"
        + " \t\"currencyCode\": \"EUR\" \n"
        + " }, \n"
        + " \"paymentTransferTypeCode\": \"INSTANTREGULAR\", \n"
        + " \"paymentTypeCode\": \"ACC2SEPA\", \n"
        + " \"source\": { \n"
        + " \t\"sourceName\": \"PAYMENT\", \n"
        + " \t\"sourceRef\": \"ID-"+ timestamp +"\" \n"
        + " } \n"
        + "}";

    @Test
    public void initializePaymentRespondsOk() {

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
            .body(initializeConfirmPaymentPayload)
        .when()
            .post("/api/v1/accounts/"+ accountId +"/payments/initialise")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", notNullValue());
    }

    @Test
    public void ConfirmPaymentRequestNoErrorsRespondsOk() {

        // No respons from api. Do not know accountId or paymentId.
        System.out.println(initializeConfirmPaymentPayload);
        String accountId = "121212121212";
        String paymentId = "12121212";

        RestAssured.baseURI = "https://account-api.sandbox.modularbank.xyz";
        given()
            .urlEncodingEnabled(true)
            .contentType(ContentType.JSON)
            .header("x-tenant-code", "SANDBOX")
            .header("x-channel-code", "SYSTEM")
            .header("x-auth-token", authToken)
        .when()
            .post("/api/v1/accounts/"+accountId+"/payments/"+paymentId+"/confirm")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", notNullValue());
    }
}
