package praktikum.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.LoginPage;
import praktikum.pages.ProfilePage;
import praktikum.support.BaseUiTest;

@DisplayName("Возврат из личного кабинета в конструктор")
class ProfileNavigationTest extends BaseUiTest {
    private ProfilePage profile;

    @BeforeEach
    void openProfile() {
        createUser();
        profile = new LoginPage(driver).open().login(user.getEmail(), user.getPassword()).openProfile();
    }

    @Test
    @DisplayName("Переход по ссылке «Конструктор»")
    void returnsViaConstructorLink() { profile.goToConstructor().assertLoggedIn(); }

    @Test
    @DisplayName("Переход по логотипу Stellar Burgers")
    void returnsViaLogo() { profile.clickLogo().assertLoggedIn(); }
}
