package praktikum.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import praktikum.model.TestUser;
import praktikum.pages.RegisterPage;
import praktikum.support.BaseUiTest;

@DisplayName("Регистрация")
class RegistrationTest extends BaseUiTest {
    @Test
    @DisplayName("Успешная регистрация с паролем из шести символов")
    void registersWithMinimumPassword() {
        TestUser generated = TestUser.unique();
        user = new TestUser(generated.getEmail(), "Pass12", generated.getName());
        RegisterPage registration = new RegisterPage(driver).open();
        trackUiRegistration();
        registration.register(user.getName(), user.getEmail(), user.getPassword())
                .waitForSuccessfulRegistration().login(user.getEmail(), user.getPassword()).assertLoggedIn();
    }

    @ParameterizedTest(name = "Ошибка для пароля длиной {0}")
    @ValueSource(ints = {1, 5})
    void rejectsShortPassword(int length) {
        TestUser generated = TestUser.unique();
        user = new TestUser(generated.getEmail(), "a".repeat(length), generated.getName());
        RegisterPage registration = new RegisterPage(driver).open();
        trackUiRegistration();
        registration.register(user.getName(), user.getEmail(), user.getPassword())
                .assertInvalidPassword();
    }
}
