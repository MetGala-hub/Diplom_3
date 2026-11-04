package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {
    private final By title = By.xpath("//h2[normalize-space()='Восстановление пароля']");
    private final By loginLink = By.cssSelector("a[href='/login']");

    public ForgotPasswordPage(WebDriver driver) { super(driver); }

    @Step("Дождаться формы восстановления пароля")
    public ForgotPasswordPage waitUntilLoaded() {
        visible(title);
        return this;
    }

    @Step("Перейти к входу из восстановления пароля")
    public LoginPage clickLogin() {
        click(loginLink);
        return new LoginPage(driver).waitUntilLoaded();
    }
}
