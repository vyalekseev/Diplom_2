package praktikum.generator;

import praktikum.request.CreateOrderRequest;

import java.util.List;

public class CreateOrderGenerator {
    public static CreateOrderRequest createOrder() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setIngredients(List.of("61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa70"));
        return createOrderRequest;
    }
    public static CreateOrderRequest orderWithoutIngredients() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setIngredients(List.of());
        return createOrderRequest;
    }
    public static CreateOrderRequest orderWithBadHash() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setIngredients(List.of("61c0c5a71d1f82001bdaaa6","61c0c5a71d1f82001bdaaa70"));
        return createOrderRequest;
    }
}
