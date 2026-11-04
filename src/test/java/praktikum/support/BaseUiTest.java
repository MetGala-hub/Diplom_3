package praktikum.support;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import praktikum.client.UserClient;
import praktikum.model.TestUser;
import static org.hamcrest.Matchers.is;

@ExtendWith(ScreenshotExtension.class)
public abstract class BaseUiTest {
    protected WebDriver driver;
    protected TestUser user;
    private String accessToken;
    private boolean registeredThroughUi;
    private final UserClient users = new UserClient();

    @BeforeEach
    @Step("Открыть браузер")
    void startBrowser() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));
        driver = DriverFactory.create();
    }

    @Step("Создать пользователя через API")
    protected void createUser() {
        user = TestUser.unique();
        Response response = users.register(user.registration());
        if (response.statusCode() == 200) { accessToken = response.path("accessToken"); }
        response.then().statusCode(200).body("success", is(true));
    }

    protected void trackUiRegistration() {
        registeredThroughUi = true;
    }

    @AfterEach
    @Step("Удалить тестового пользователя и закрыть браузер")
    void tearDown() {
        try {
            if (accessToken != null) {
                users.delete(accessToken).then().statusCode(202).body("success", is(true));
            } else if (registeredThroughUi) {
                Response login = users.login(user.credentials());
                // В тесте неуспешной регистрации пользователь не создаётся.
                if (login.statusCode() != 401) {
                    String token = login.then().statusCode(200).body("success", is(true))
                            .extract().path("accessToken");
                    users.delete(token).then().statusCode(202).body("success", is(true));
                }
            }
        } finally {
            if (driver != null) { driver.quit(); }
        }
    }
}
