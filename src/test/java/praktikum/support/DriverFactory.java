package praktikum.support;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private DriverFactory() { }

    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080", "--no-first-run", "--no-default-browser-check");
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
        }
        WebDriver driver;
        if (browser.equals("yandex")) {
            String defaultBinary = System.getenv("LOCALAPPDATA") + "\\Yandex\\YandexBrowser\\Application\\browser.exe";
            String binary = System.getProperty("yandex.binary", defaultBinary);
            String executable = System.getProperty("yandex.driver", "");
            if (!Files.isRegularFile(Path.of(binary)) || !Files.isRegularFile(Path.of(executable))) {
                throw new IllegalArgumentException("Укажите -Dyandex.binary=<browser.exe> и -Dyandex.driver=<yandexdriver.exe>");
            }
            options.setBinary(binary);
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(Path.of(executable).toFile()).build();
            driver = new ChromeDriver(service, options);
        } else if (browser.equals("chrome")) {
            String binary = System.getProperty("chrome.binary", "");
            if (!binary.isBlank()) { options.setBinary(binary); }
            driver = new ChromeDriver(options);
        } else {
            throw new IllegalArgumentException("Поддерживаются browser=chrome и browser=yandex");
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        return driver;
    }
}
