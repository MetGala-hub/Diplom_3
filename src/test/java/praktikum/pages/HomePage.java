package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePage extends BasePage {
    private final By title = By.xpath("//h1[normalize-space()='Соберите бургер']");
    private final By signInButton = By.xpath("//button[normalize-space()='Войти в аккаунт']");
    private final By placeOrderButton = By.xpath("//button[normalize-space()='Оформить заказ']");

    public enum Section {
        BUNS("Булки"), SAUCES("Соусы"), FILLINGS("Начинки");
        private final String title;
        Section(String title) { this.title = title; }
        public String getTitle() { return title; }
    }

    public HomePage(WebDriver driver) { super(driver); }

    private By tab(Section section) {
        return By.xpath("//span[normalize-space()='" + section.getTitle() + "']/parent::div");
    }

    @Step("Открыть главную страницу")
    public HomePage open() {
        driver.get(baseUrl + "/");
        return waitUntilLoaded();
    }

    @Step("Дождаться загрузки конструктора")
    public HomePage waitUntilLoaded() {
        visible(title);
        return this;
    }

    @Step("Открыть вход по кнопке на главной странице")
    public LoginPage clickSignIn() {
        click(signInButton);
        return new LoginPage(driver).waitUntilLoaded();
    }

    @Step("Открыть вход через личный кабинет")
    public LoginPage openLoginViaAccount() {
        click(accountLink);
        return new LoginPage(driver).waitUntilLoaded();
    }

    @Step("Открыть личный кабинет авторизованного пользователя")
    public ProfilePage openProfile() {
        click(accountLink);
        return new ProfilePage(driver).waitUntilLoaded();
    }

    @Step("Проверить успешный вход: доступна кнопка оформления заказа")
    public void assertLoggedIn() {
        assertTrue(visible(placeOrderButton).isDisplayed());
    }

    @Step("Выбрать раздел {section}")
    public HomePage selectSection(Section section) {
        click(tab(section));
        return this;
    }

    @Step("Проверить, что выбран раздел {section}")
    public void assertSectionSelected(Section section) {
        assertTrue(wait.until(ExpectedConditions.attributeContains(tab(section), "class", "tab_tab_type_current")));
    }
}
