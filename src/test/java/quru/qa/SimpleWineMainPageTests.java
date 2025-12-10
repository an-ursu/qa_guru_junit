package quru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import quru.qa.data.WineFilters;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SimpleWineMainPageTests {

    private static boolean ageConfirmed = false;
    private static boolean cityConfirmed = false;

    @BeforeEach
    void preconditions() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        open("https://simplewine.ru/");

        if (!ageConfirmed) {
            try {
                $(".AgeConfirmModal_button__Kiyo1")
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .click();

                ageConfirmed = true;
            } catch (ElementNotFound | TimeoutException e) {

            }
        }

        if (!cityConfirmed) {
            try {
                $(".CityClarificationPopup_closerIcon__Q4R6a")
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .click();

                cityConfirmed = true;
            } catch (ElementNotFound | TimeoutException e) {

            }
        }
    }

    @ValueSource(strings = {
            "В подарок", "Вино", "Шампанское и игристое", "Виски", "Коньяк", "Крепкие напитки", "Вода", "Бокалы",
            "Аксессуары", "Fine & Rare", "Блог", "Дегустации", "Акции %"

    })
    @ParameterizedTest(name = "Категория {0} должна быть видима на главной странице")
    @Tag("WEB")
    void categoryBlocksShouldBeVisibleOnMainPage(String categoryName) {
        $(".BasicHeader_headerExtra__Lo15z")
                .shouldBe(visible)
                .shouldHave(text(categoryName));
    }


    @CsvSource({
            "Pinot Noir, Pinot Noir",
            "Mac-Talla, Mac-Talla"
    })
    @ParameterizedTest(name = "Поиск товара {0}")
    @Tag("WEB")
    void productCardShouldBeMatchSearchQuery(String searchQuery, String productCardName) {
        $("[data-autotest-target-id='header-search-input']").setValue(searchQuery);
        $("[data-autotest-target-id='header-search-go']").click();
        $(".catalog-items__block.js-catalog-items.catalog-items__block-search").shouldHave(text(productCardName));
    }


    @EnumSource(WineFilters.class)
    @ParameterizedTest(name = "Проверка началичия фильтра {0} для категории Вино")
    void categoryVineContainsFilters(WineFilters wineFilters) {
        $("[data-autotest-target-id='header-ecom-catalog-btn']").click();
        $(".CatalogMenuSection_catalogMenuSectionItemName__j-eD-").hover();
        $(".CatalogMenuSectionCategories_categories__-jC8r").shouldHave(text(wineFilters.wineFiltersName));

    }

}
