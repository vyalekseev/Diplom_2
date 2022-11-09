import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.CreateUserMethods;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static praktikum.generator.CreateUserGenerator.createUser;
import static praktikum.generator.CreateUserGenerator.createUserWithoutName;

public class CreateUserTest {
    private CreateUserMethods createUserMethods;
    private String bearerToken;

    @Before
    public void setUp() {
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
    public void addUser() {
        CreateUserRequest randomUser = createUser();
        String accessToken = createUserMethods.create(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
        bearerToken = accessToken.replaceAll("Bearer ", "");
    }

    @Test
    public void addExistsUser() {
        CreateUserRequest randomUser = createUser();
        String accessToken = createUserMethods.create(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
        bearerToken = accessToken.replaceAll("Bearer ", "");

        createUserMethods.create(randomUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("User already exists"));
    }

    @Test
    public void addUserWithoutName() {
        CreateUserRequest newUser = createUserWithoutName();
        createUserMethods.create(newUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

}
