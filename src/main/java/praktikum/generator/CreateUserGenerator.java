package praktikum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import praktikum.request.CreateUserRequest;

public class CreateUserGenerator {
    public static CreateUserRequest createUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(RandomStringUtils.randomAlphabetic(10) + "@test.ru");
        createUserRequest.setPassword(RandomStringUtils.randomAlphanumeric(6));
        createUserRequest.setName(RandomStringUtils.randomAlphabetic(5));
        return createUserRequest;
    }

    public static CreateUserRequest createUserWithoutName() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(RandomStringUtils.randomAlphabetic(10) + "@test.ru");
        createUserRequest.setPassword(RandomStringUtils.randomAlphanumeric(6));
        return createUserRequest;
    }
}
