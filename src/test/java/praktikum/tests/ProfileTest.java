package praktikum.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.LoginPage;
import praktikum.support.BaseUiTest;

@DisplayName("Личный кабинет")
class ProfileTest extends BaseUiTest {
    @Test
    @DisplayName("Переход в личный кабинет показывает данные пользователя")
    void opensPersonalAccount() {
        createUser();
        new LoginPage(driver).open().login(user.getEmail(), user.getPassword()).openProfile()
                .assertUserData(user.getName(), user.getEmail());
    }
}
