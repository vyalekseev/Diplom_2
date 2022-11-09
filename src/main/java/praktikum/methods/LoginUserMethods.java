package praktikum.methods;

import io.restassured.response.ValidatableResponse;
import praktikum.RestClient;
import praktikum.request.CreateUserRequest;

import static io.restassured.RestAssured.given;

public class LoginUserMethods extends RestClient {
    public ValidatableResponse login(CreateUserRequest createUserRequest) {
        return given().log().all()
                .spec(getDefaultRequest())
                .body(createUserRequest)
                .post("/api/auth/login")
                .prettyPeek()
                .then();
    }
}
