package praktikum.methods;

import io.restassured.response.ValidatableResponse;
import praktikum.RestClient;

import static io.restassured.RestAssured.given;

public class GetOrdersMethods extends RestClient {
    public ValidatableResponse getOrdersWithAuthorization(String bearerToken) {
        return given().log().all()
                .spec(getDefaultRequest())
                .auth().oauth2(bearerToken)
                .get("/api/orders")
                .prettyPeek()
                .then();
    }

    public ValidatableResponse getOrdersWithoutAuthorization() {
        return given().log().all()
                .spec(getDefaultRequest())
                .get("/api/orders")
                .prettyPeek()
                .then();
    }
}
