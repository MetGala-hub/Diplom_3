package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends BasePage {
    private final By title = By.xpath("//h2[normalize-space()='Вход']");
    private final By emailInput = input("Email");
    private final By passwordInput = input("Пароль");
    private final By signInButton = By.xpath("//button[normalize-space()='Войти']");
    private final By registerLink = By.cssSelector("a[href='/register']");
    private final By forgotPasswordLink = By.cssSelector("a[href='/forgot-password']");

    public LoginPage(WebDriver driver) { super(driver); }

    @Step("Открыть страницу входа")
    public LoginPage open() {
        driver.get(baseUrl + "/login");
        return waitUntilLoaded();
    }

    @Step("Дождаться страницы входа")
    public LoginPage waitUntilLoaded() {
        visible(title);
        return this;
    }

    @Step("Войти с почтой {email}")
    public HomePage login(String email, String password) {
        fill(emailInput, email);
        fill(passwordInput, password);
        click(signInButton);
        return new HomePage(driver).waitUntilLoaded();
    }

    @Step("Открыть форму регистрации")
    public RegisterPage openRegistration() {
        click(registerLink);
        return new RegisterPage(driver).waitUntilLoaded();
    }

    @Step("Открыть форму восстановления пароля")
    public ForgotPasswordPage openPasswordRecovery() {
        click(forgotPasswordLink);
        return new ForgotPasswordPage(driver).waitUntilLoaded();
    }

    @Step("Проверить открытие страницы входа")
    public void assertOpened() {
        assertTrue(wait.until(ExpectedConditions.urlToBe(baseUrl + "/login")));
        assertTrue(visible(signInButton).isDisplayed());
    }
}
