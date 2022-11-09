import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.ChangeUserProfileMethods;
import praktikum.methods.CreateUserMethods;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static praktikum.generator.CreateUserGenerator.createUser;

public class ChangeUserProfileTest {
    private CreateUserMethods createUserMethods;
    private ChangeUserProfileMethods changeUserProfileMethods;
    private String bearerToken;
    private CreateUserRequest randomUser = createUser();

    @Before
    public void setUp() {
        createUserMethods = new CreateUserMethods();
        changeUserProfileMethods = new ChangeUserProfileMethods();

        String accessToken = createUserMethods.create(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
        bearerToken = accessToken.replaceAll("Bearer ", "");
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
    public void updateUserWithAuthorization() {
        CreateUserRequest updateUser = createUser();
        changeUserProfileMethods.updateInfoAuthorization(updateUser, bearerToken)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(updateUser.getEmail().toLowerCase()))
                .and()
                .body("user.name", equalTo(updateUser.getName()));
    }

@Test
    public void updateUserWithoutAuthorization() {
        CreateUserRequest updateUser = createUser();
        changeUserProfileMethods.updateInfoWithoutAuthorization(updateUser)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
