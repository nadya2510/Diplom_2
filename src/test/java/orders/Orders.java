package orders;

import base.ApiStellarburgers;
import io.restassured.response.Response;


public class Orders extends ApiStellarburgers {
    public static final String URL_ORDERS = "/api/orders";

    //Создание заказа
    public Response addOrders (OrdersIn bodyJason, String token) {
        return doPostRequest(URL_ORDERS , bodyJason, token);
    }
    //Получить заказы конкретного пользователя
    public Response getListOrders (String token) {
        return doGetRequest(URL_ORDERS , token);
    }


}
