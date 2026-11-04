package praktikum.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import praktikum.pages.HomePage;
import praktikum.pages.HomePage.Section;
import praktikum.support.BaseUiTest;

@DisplayName("Разделы конструктора")
class ConstructorTest extends BaseUiTest {
    @ParameterizedTest(name = "Переход к разделу {0}")
    @EnumSource(Section.class)
    void switchesIngredientSection(Section section) {
        HomePage home = new HomePage(driver).open();
        // «Булки» выбраны при открытии: сначала уходим на другую вкладку.
        if (section == Section.BUNS) { home.selectSection(Section.SAUCES).assertSectionSelected(Section.SAUCES); }
        home.selectSection(section).assertSectionSelected(section);
    }
}
