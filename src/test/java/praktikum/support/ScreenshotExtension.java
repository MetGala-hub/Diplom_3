package praktikum.support;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        BaseUiTest test = (BaseUiTest) context.getRequiredTestInstance();
        if (context.getExecutionException().isPresent() && test.driver != null) {
            try {
                Allure.addAttachment("Страница при ошибке", "image/png", new ByteArrayInputStream(
                        ((TakesScreenshot) test.driver).getScreenshotAs(OutputType.BYTES)), ".png");
            } catch (RuntimeException e) {
                context.getExecutionException().get().addSuppressed(e);
            }
        }
    }
}
