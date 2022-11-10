import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.CreateOrderMethods;
import praktikum.methods.CreateUserMethods;
import praktikum.methods.GetOrdersMethods;
import praktikum.request.CreateOrderRequest;
import praktikum.request.CreateUserRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static praktikum.generator.CreateOrderGenerator.createOrder;
import static praktikum.generator.CreateUserGenerator.createUser;

public class GetOrderTest {
    private CreateUserMethods createUserMethods;
    private String bearerToken;
    private CreateUserRequest randomUser = createUser();
    private CreateOrderMethods createOrderMethods;
    private GetOrdersMethods getOrdersMethods;

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
    @DisplayName("Получение заказа авторизованным пользователем")
    public void getOrderWithAuthorization() {
        CreateOrderRequest ingredients = createOrder();
        createOrderMethods = new CreateOrderMethods();

        createOrderMethods.createOrderWithAuthorization(ingredients, bearerToken)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("order.ingredients[0]._id", equalTo(ingredients.getIngredients().get(0)))
                .and()
                .body("order.ingredients[1]._id", equalTo(ingredients.getIngredients().get(1)));

        getOrdersMethods = new GetOrdersMethods();
        getOrdersMethods.getOrdersWithAuthorization(bearerToken)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("orders[0].ingredients", equalTo(ingredients.getIngredients()));
    }

    @Test
    @DisplayName("Получение заказа без авторизации")
    public void getOrderWithoutAuthorization() {
        getOrdersMethods = new GetOrdersMethods();
        getOrdersMethods.getOrdersWithoutAuthorization()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
