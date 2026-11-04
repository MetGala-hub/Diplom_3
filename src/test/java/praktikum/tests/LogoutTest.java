package praktikum.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.HomePage;
import praktikum.pages.LoginPage;
import praktikum.support.BaseUiTest;

@DisplayName("Выход из аккаунта")
class LogoutTest extends BaseUiTest {
    @Test
    @DisplayName("После выхода личный кабинет требует повторного входа")
    void logsOutAndProtectsAccount() {
        createUser();
        new LoginPage(driver).open().login(user.getEmail(), user.getPassword()).openProfile()
                .logout().assertOpened();
        new HomePage(driver).open().openLoginViaAccount().assertOpened();
    }
}
