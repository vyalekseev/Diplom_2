import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.ChangeUserProfileMethods;
import praktikum.methods.CreateUserMethods;
import praktikum.methods.LoginUserMethods;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static praktikum.generator.CreateUserGenerator.createUser;

public class ChangeUserProfile {
    private CreateUserMethods createUserMethods;
    private ChangeUserProfileMethods changeUserProfileMethods;
    private String bearerToken;
    private CreateUserRequest randomUser = createUser();

    @Before
    public void setUp() {
        createUserMethods = new CreateUserMethods();
        changeUserProfileMethods = new ChangeUserProfileMethods();

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
    public void updateUser() {
        String accessToken = createUserMethods.create(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
        bearerToken = accessToken.replaceAll("Bearer ", "");

        CreateUserRequest updateUser = createUser();
        changeUserProfileMethods.updateInfo(updateUser, bearerToken)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(updateUser.getEmail()))
                .and()
                .body("user.name", equalTo(updateUser.getName()));
    }
}
