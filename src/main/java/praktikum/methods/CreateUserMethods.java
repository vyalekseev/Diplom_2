package praktikum.methods;

import io.restassured.response.ValidatableResponse;
import praktikum.RestClient;
import praktikum.request.CreateUserRequest;

import static io.restassured.RestAssured.given;

public class CreateUserMethods extends RestClient {

    public ValidatableResponse create(CreateUserRequest createUserRequest) {
        return given().log().all()
                .spec(getDefaultRequest())
                .body(createUserRequest)
                .post("/api/auth/register")
                .prettyPeek()
                .then();

    }

    public ValidatableResponse delete(String bearerToken) {
        return given().log().all()
                .spec(getDefaultRequest())
                .auth().oauth2(bearerToken)
                .delete("/api/auth/user")
                .prettyPeek()
                .then();
    }
}
