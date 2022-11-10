package praktikum.methods;

import io.restassured.response.ValidatableResponse;
import praktikum.RestClient;
import praktikum.request.CreateOrderRequest;
import praktikum.request.CreateUserRequest;

import static io.restassured.RestAssured.given;

public class CreateOrderMethods extends RestClient {
    public ValidatableResponse createOrderWithAuthorization(CreateOrderRequest createOrderRequest, String bearerToken) {
        return given().log().all()
                .spec(getDefaultRequest())
                .auth().oauth2(bearerToken)
                .body(createOrderRequest)
                .post("/api/orders")
                .prettyPeek()
                .then();
    }
    public ValidatableResponse createOrderWithoutAuthorization(CreateOrderRequest createOrderRequest) {
        return given().log().all()
                .spec(getDefaultRequest())
                .body(createOrderRequest)
                .post("/api/orders")
                .prettyPeek()
                .then();
    }
}
