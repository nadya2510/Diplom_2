package orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class OrdersStep {

    private Orders orders = new Orders();
    private ValidatableResponse response;


    // Создание заказа с авторизацией с ингредиентами
    @Step("Send Post add orders request ")
    public Integer postRequestAddOrders(OrdersIn ordersIn, String token) {
        Integer number = 0;
        //добавим заказ
        Response responseOrd = orders.addOrders(ordersIn, token);
        if (responseOrd.statusCode() == 200){
           number =  responseOrd.then().extract().body().path("order.number");
        }
        response =  responseOrd.then();
        return  number;
    }


    // Проверяем ответ по коду и тело
    @Step("Check orders: code, body")
    public void checkOrdersRegisterCodeBody(Integer statusCode, Boolean success, String message, Integer number) {
        response.assertThat().statusCode(statusCode);

        if (success != null) {
            Boolean responseSuccess = response.extract().body().path("success");
            MatcherAssert.assertThat(responseSuccess,is(success));
        }

        if (message != ""){
            String responseMessage =  response.extract().body().path("message");
            MatcherAssert.assertThat(responseMessage, equalTo(message));
        }

        if (number != null){
            OrdersOut ordersOut = response.extract().body().as(OrdersOut.class);
            List<String> listNumber = new ArrayList<>();
            List<OrdersList>  ordersList = ordersOut.getOrders();
            Integer responseNumber =  ordersList.get(0).getNumber();
            Assert.assertEquals(responseNumber, number);
        }
    }

    // Получить заказы конкретного пользователя
    @Step("Send Get list orders user request")
    public void getRequestOrdersListUser(String token) {

        response = orders.getListOrders(token).then();
    }



}
