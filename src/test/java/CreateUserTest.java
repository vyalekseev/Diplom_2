import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.CreateUserMethods;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static praktikum.generator.CreateUserGenerator.createUser;

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
}
