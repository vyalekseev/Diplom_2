import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.CreateUserMethods;
import praktikum.methods.LoginUserMethods;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static praktikum.generator.CreateUserGenerator.createUser;

public class LoginUserTest {
    private LoginUserMethods loginUserMethods;
    private CreateUserMethods createUserMethods;
    private String bearerToken;
    @Before
    public void setUp() {
        loginUserMethods = new LoginUserMethods();
        createUserMethods = new CreateUserMethods();
    }

    @After
    public void tearDown() {
        if (bearerToken != null) {
            createUserMethods.delete(bearerToken)
                    .assertThat()
                    .body("success", equalTo(true));
        }
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void loginUser() {
        CreateUserRequest randomUser = createUser();
        String accessToken = createUserMethods.create(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
        bearerToken = accessToken.replaceAll("Bearer ", "");


        loginUserMethods.login(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("Авторизация несуществующего пользователя")
    public void loginUserNotExist() {
        CreateUserRequest randomUser = createUser();
        loginUserMethods.login(randomUser)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message",equalTo("email or password are incorrect"));
    }
}
