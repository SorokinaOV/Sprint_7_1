import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrdersSteps extends RestConfig {
    private static final String ORDERS = "/api/v1/orders";
    private static final String LIST = "/api/v1/orders";
    private static final String CANCEL_ORDERS = "/api/v1/orders/cancel";

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(HOST);
    }

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(Orders orders) {
        return requestSpec()
                .body(orders)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получение списка закаgitзов")
    public ValidatableResponse getOrderList() {
        return requestSpec()
                .when()
                .get(LIST)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return requestSpec()
                .body(track)
                .when()
                .put(CANCEL_ORDERS)
                .then();
    }
}
