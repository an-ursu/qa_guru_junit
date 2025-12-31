package quru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import quru.qa.model.ProductList;
import quru.qa.model.ProductMenu;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTests {

    private final ClassLoader cl = JsonTests.class.getClassLoader();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void jsonParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(cl.getResourceAsStream("holidays_menu.json")), StandardCharsets.UTF_8)) {

            ProductMenu menu = objectMapper.readValue(reader, ProductMenu.class);

            assertNotNull(menu, "Объект меню не должен быть null");
            assertEquals("251C13791", menu.getParent_id(), "Неверный parent_id");
            assertEquals("Праздничное меню", menu.getName(), "Неверное название меню");
            assertFalse(menu.getProducts().isEmpty());

            ProductList firstProduct = menu.getProducts().get(0);
            assertNotNull(firstProduct, "Первый продукт не должен быть null");

            assertEquals(3148279, firstProduct.getPlu(), "Неверный PLU");
            assertEquals("Авокадо Global Village Хасс 2шт.", firstProduct.getProduct_name(),
                    "Неверное название продукта");
            assertEquals("шт", firstProduct.getUom(), "Неверная единица измерения");
            assertEquals("199.99", firstProduct.getPrices().getRegular(),
                    "Цены не должны быть null");
            assertTrue(firstProduct.isIs_available(), "Продукт должен быть доступен");
        }
    }

}
