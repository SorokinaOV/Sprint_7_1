import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {
    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter());
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что список заказов успешно получен")
    public void getOrderList() {
        OrdersSteps orderSteps = new OrdersSteps();
        ValidatableResponse responseOrderList = orderSteps.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}

