package praktikum.pages;

import java.time.Duration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final String baseUrl = System.getProperty("baseUrl", "https://qa-stellarburgers.education-services.ru");
    protected final By accountLink = By.xpath("//a[.//p[normalize-space()='Личный Кабинет' or normalize-space()='Личный кабинет']]");
    private final By constructorLink = By.xpath("//a[.//p[normalize-space()='Конструктор']]");
    private final By logoLink = By.cssSelector("header div[class*='logo'] a");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void fill(By locator, String text) {
        WebElement input = visible(locator);
        input.clear();
        input.sendKeys(text);
    }

    protected static By input(String label) {
        return By.xpath("//label[normalize-space()='" + label + "']/following-sibling::input");
    }

    @Step("Перейти в конструктор через меню")
    public HomePage goToConstructor() {
        click(constructorLink);
        return new HomePage(driver).waitUntilLoaded();
    }

    @Step("Перейти в конструктор по логотипу")
    public HomePage clickLogo() {
        click(logoLink);
        return new HomePage(driver).waitUntilLoaded();
    }
}
