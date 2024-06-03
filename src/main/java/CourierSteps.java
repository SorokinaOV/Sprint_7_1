import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierSteps extends RestConfig {
    private static final String COURIER = "/api/v1/courier";
    private static final String LOGIN = "/api/v1/courier/login";
    private static final String COURIER_DELETE = "/api/v1/courier/{id}";

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .when()
                .delete(COURIER_DELETE + id)
                .then();
    }
}
