package praktikum;

import io.restassured.RestAssured;

public class Config {
    private static String BASE_URI;

    public static String getBaseUri() {
        BASE_URI = RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        return BASE_URI;
    }

}
