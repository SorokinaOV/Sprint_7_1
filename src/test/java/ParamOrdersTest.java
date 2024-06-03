import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class ParamOrdersTest {
    private final List<String> colour;
    private int track;
    private OrdersSteps ordersSteps;

    public ParamOrdersTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] getScooterColour() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK, GRAY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        ordersSteps = new OrdersSteps();
    }

    @Test
    @DisplayName("Размещение заказа с самокатами разных цветов")
    @Description("Проверяем корректность размещения заказа с самокатами разных цветов")
    public void orderingWithScootersInDifferentColors() {
        Orders orders = new Orders(colour);
        ValidatableResponse responseCreateOrder = ordersSteps.createNewOrder(orders);
        track = responseCreateOrder.extract().path("track");
        responseCreateOrder.assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }

    @After
    @Step("Отменить заказ")
    public void сancelTestOrder() {
        ordersSteps.cancelOrder(track);
    }
}
