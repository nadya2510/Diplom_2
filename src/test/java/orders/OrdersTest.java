package orders;

import ingredients.IngredientsStep;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import register.AuthStep;
import register.RegisterIn;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdersTest {
    // данные для создать уникального пользователя
    AuthStep authStep = new AuthStep();
    Integer random = new Random().nextInt();
    String email = String.format("test-data%d@yandex.ru", random);
    String password = "password123";
    String username = String.format("Username%d", random);
    RegisterIn json  = new RegisterIn(email, password, username);
    // создать уникального пользователя
    String  accessToken = authStep.postRequestAuthRegister(json);
    //для авторизации пользователя
    RegisterIn jsonLog;
    OrdersStep ordersStep = new OrdersStep();



    @Test
    @DisplayName("200-Check orders add- login, ingredients add true")
    @Description("Status code 200 test")
    public void checkCreateOrderLoginAddIngred() {
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //для создания заказа
        IngredientsStep ingredientsStep = new IngredientsStep();
        List<String> ingredOrder = new ArrayList<>();
        //список ингредиентов
        List<String> ingredId = ingredientsStep.getRequestIngredientsList();
        //добавим в заказ первые 2
        ingredOrder.add(ingredId.get(0));
        ingredOrder.add(ingredId.get(1));
        OrdersIn ordersIn = new OrdersIn(ingredOrder);
        //добавим заказ
        ordersStep.postRequestAddOrders(ordersIn,accessTokenLog);
        ordersStep.checkOrdersRegisterCodeBody(200, true, "", null);
    }

    @Test
    @DisplayName("500-Check orders add- login, ingredients add false")
    @Description("Status code 500 test")
    public void checkCreateOrderLoginAddIngredError() {
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //для создания заказа  с неверным хешем ингредиентов
        List<String> ingredOrder = new ArrayList<>();
        ingredOrder.add("999");
        OrdersIn ordersIn = new OrdersIn(ingredOrder);
        //добавим заказ
        ordersStep.postRequestAddOrders(ordersIn,accessTokenLog);
        ordersStep.checkOrdersRegisterCodeBody(500, null, "", null);
    }

    @Test
    @DisplayName("400-Check orders add- login, not ingredients ")
    @Description("Status code 400 test")
    public void checkCreateOrderLoginAddIngredNot() {
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //для создания заказа  с неверным хешем ингредиентов
        List<String> ingredOrder = new ArrayList<>();
        OrdersIn ordersIn = new OrdersIn(ingredOrder);
        //добавим заказ без ингредиентов
        ordersStep.postRequestAddOrders(ordersIn,accessTokenLog);
        ordersStep.checkOrdersRegisterCodeBody(400, false, "Ingredient ids must be provided", null);
    }

    @Test
    @DisplayName("200-Check orders add- logout, ingredients add true")
    @Description("Status code 200 test")
    public void checkCreateOrderLogoutAddIngred() {
        //нет авторизации пользователя только тело запроса, для дальнейшего удаления
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //для создания заказа
        IngredientsStep ingredientsStep = new IngredientsStep();
        List<String> ingredOrder = new ArrayList<>();
        //список ингредиентов
        List<String> ingredId = ingredientsStep.getRequestIngredientsList();
        //добавим в заказ первые 2
        ingredOrder.add(ingredId.get(0));
        ingredOrder.add(ingredId.get(1));
        OrdersIn ordersIn = new OrdersIn(ingredOrder);
        //добавим заказ
        ordersStep.postRequestAddOrders(ordersIn,accessTokenLog);
        ordersStep.checkOrdersRegisterCodeBody(200, true, "", null);
    }

    @Test
    @DisplayName("200-Check get orders login")
    @Description("Status code 200 test")
    public void checkGetOrderLogin() {
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //для создания заказа
        IngredientsStep ingredientsStep = new IngredientsStep();
        List<String> ingredOrder = new ArrayList<>();
        //список ингредиентов
        List<String> ingredId = ingredientsStep.getRequestIngredientsList();
        //добавим в заказ первые 2
        ingredOrder.add(ingredId.get(0));
        ingredOrder.add(ingredId.get(1));
        OrdersIn ordersIn = new OrdersIn(ingredOrder);
        //добавим заказ
        Integer number = ordersStep.postRequestAddOrders(ordersIn,accessTokenLog);
        //получим список заказ
        ordersStep.getRequestOrdersListUser(accessTokenLog);
        ordersStep.checkOrdersRegisterCodeBody(200, true, "", number);
    }

    @Test
    @DisplayName("401-Check get orders logout")
    @Description("Status code 401 test")
    public void checkGetOrderLogout() {
        //получим список заказ
        ordersStep.getRequestOrdersListUser("");
        ordersStep.checkOrdersRegisterCodeBody(401, false, "You should be authorised", null);
    }

    @After
    public void deleteUser() {
        if (jsonLog != null) {
            String accessToken = authStep.postRequestAuthLogin(jsonLog);
            authStep.deleteUser(accessToken);
        }
    }
}
