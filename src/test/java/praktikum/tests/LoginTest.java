package praktikum.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.HomePage;
import praktikum.pages.LoginPage;
import praktikum.support.BaseUiTest;

@DisplayName("Вход в аккаунт")
class LoginTest extends BaseUiTest {
    @BeforeEach
    void prepareUser() { createUser(); }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт»")
    void logsInFromHomeButton() {
        new HomePage(driver).open().clickSignIn().login(user.getEmail(), user.getPassword()).assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через «Личный кабинет»")
    void logsInFromAccountLink() {
        new HomePage(driver).open().openLoginViaAccount().login(user.getEmail(), user.getPassword()).assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    void logsInFromRegistration() {
        new LoginPage(driver).open().openRegistration().clickLogin()
                .login(user.getEmail(), user.getPassword()).assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    void logsInFromPasswordRecovery() {
        new LoginPage(driver).open().openPasswordRecovery().clickLogin()
                .login(user.getEmail(), user.getPassword()).assertLoggedIn();
    }
}
