package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

public class ProfilePage extends BasePage {
    private final By nameInput = input("Имя");
    private final By emailInput = input("Логин");
    private final By logoutButton = By.xpath("//button[normalize-space()='Выход' or normalize-space()='Выйти']");

    public ProfilePage(WebDriver driver) { super(driver); }

    @Step("Дождаться личного кабинета")
    public ProfilePage waitUntilLoaded() {
        visible(logoutButton);
        return this;
    }

    @Step("Проверить имя и почту в личном кабинете")
    public void assertUserData(String name, String email) {
        assertTrue(wait.until(ExpectedConditions.urlContains("/account/profile")));
        assertTrue(wait.until(ExpectedConditions.attributeToBe(nameInput, "value", name)));
        assertTrue(wait.until(ExpectedConditions.attributeToBe(emailInput, "value", email)));
    }

    @Step("Выйти из аккаунта")
    public LoginPage logout() {
        click(logoutButton);
        return new LoginPage(driver).waitUntilLoaded();
    }
}
