package praktikum.methods;

import io.restassured.response.ValidatableResponse;
import praktikum.RestClient;
import praktikum.request.CreateUserRequest;

import static io.restassured.RestAssured.given;

public class ChangeUserProfileMethods extends RestClient {
    public ValidatableResponse updateInfo(CreateUserRequest createUserRequest, String bearerToken) {
        return given().log().all()
                .spec(getDefaultRequest())
                .auth().oauth2(bearerToken)
                .body(createUserRequest)
                .patch("/api/auth/user")
                .prettyPeek()
                .then();
    }
}
