import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.CreateOrderMethods;
import praktikum.methods.CreateUserMethods;
import praktikum.request.CreateOrderRequest;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static praktikum.generator.CreateOrderGenerator.*;
import static praktikum.generator.CreateUserGenerator.createUser;

public class CreateOrderTest {
    private CreateUserMethods createUserMethods;
    private String bearerToken;
    private CreateUserRequest randomUser = createUser();

    private CreateOrderMethods createOrderMethods;

    @Before
    public void setUp() {
        createUserMethods = new CreateUserMethods();

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
    public void createOrderWithAuthorization() {
        CreateOrderRequest ingredients = createOrder();
        createOrderMethods = new CreateOrderMethods();

        createOrderMethods.createOrderWithAuthorization(ingredients,bearerToken)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("order.ingredients[0]._id", equalTo(ingredients.getIngredients().get(0)))
                .and()
                .body("order.ingredients[1]._id", equalTo(ingredients.getIngredients().get(1)));
    }

    @Test
    public void createOrderWithoutAuthorization() {
        CreateOrderRequest ingredients = createOrder();
        createOrderMethods = new CreateOrderMethods();

        createOrderMethods.createOrderWithoutAuthorization(ingredients)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("order.number", notNullValue());
    }

    @Test
    public void createOrderWithoutIngredients() {
        CreateOrderRequest ingredients = orderWithoutIngredients();
        createOrderMethods = new CreateOrderMethods();

        createOrderMethods.createOrderWithoutAuthorization(ingredients)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo( "Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithWrongHash() {
        CreateOrderRequest ingredients = orderWithBadHash();
        createOrderMethods = new CreateOrderMethods();

        createOrderMethods.createOrderWithAuthorization(ingredients,bearerToken)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);

    }

}
