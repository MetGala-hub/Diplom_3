package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterPage extends BasePage {
    private final By title = By.xpath("//h2[normalize-space()='Регистрация']");
    private final By nameInput = input("Имя");
    private final By emailInput = input("Email");
    private final By passwordInput = input("Пароль");
    private final By registerButton = By.xpath("//button[normalize-space()='Зарегистрироваться']");
    private final By loginLink = By.cssSelector("a[href='/login']");
    private final By passwordError = By.xpath("//p[normalize-space()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) { super(driver); }

    @Step("Открыть страницу регистрации")
    public RegisterPage open() {
        driver.get(baseUrl + "/register");
        return waitUntilLoaded();
    }

    @Step("Дождаться формы регистрации")
    public RegisterPage waitUntilLoaded() {
        visible(title);
        return this;
    }

    @Step("Заполнить и отправить форму регистрации")
    public RegisterPage register(String name, String email, String password) {
        fill(nameInput, name);
        fill(emailInput, email);
        fill(passwordInput, password);
        click(registerButton);
        return this;
    }

    @Step("Дождаться перехода к входу после регистрации")
    public LoginPage waitForSuccessfulRegistration() {
        return new LoginPage(driver).waitUntilLoaded();
    }

    @Step("Перейти к входу из регистрации")
    public LoginPage clickLogin() {
        click(loginLink);
        return new LoginPage(driver).waitUntilLoaded();
    }

    @Step("Проверить ошибку короткого пароля")
    public void assertInvalidPassword() {
        assertEquals("Некорректный пароль", visible(passwordError).getText());
        assertTrue(visible(registerButton).isDisplayed());
    }
}
