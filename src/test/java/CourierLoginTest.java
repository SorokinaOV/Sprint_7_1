import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    int id;

    @Before
    @Step("Создание тестовых данных для логина курьера")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter());

        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        courier.setPassword(RandomStringUtils.randomAlphanumeric(10));
    }

    @Test
    @DisplayName("Логин курьера успешен")
    @Description("Проверяем, что курьер может войти в систему с валидными данными")
    public void shouldReturnId() {
        courierSteps
                .createCourier(courier);

        courierSteps
                .login(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера с пустым полем логина")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем логина")
    public void courierLoginErrorAccountNotFound() {
        courier.setLogin("");
        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Логин курьера с пустым полем пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем пароля")
    public void courierPasswordErrorAccountNotFound() {
        courier.setPassword("");
        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Логин курьера с пустым полями логина и пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустыми полями логина и пароля")
    public void courierLoginPasswordErrorAccountNotFound() {
        courier.setPassword("");
        courier.setLogin("");
        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Логин курьера был ранее зарегистрирован")
    @Description("Проверяем, что курьера не может войти в ситсему с ранее зарегистрированным логином")
    public void courierCanNotBeCreatedWithSameLogin() {
        courierSteps.createCourier(courier);
        courierSteps
                .createCourier(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Логин курьера c невалидным логином")
    @Description("Проверяем, что курьер не может войти в систему с ранее не зарегистрированным логином")
    public void courierInvalidLoginErrorAccountNotFound() {
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        courierSteps
                .login(courier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (id != 0) {
            courierSteps.delete(id);
        }
    }

}
