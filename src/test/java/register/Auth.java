package register;

import base.ApiStellarburgers;
import io.restassured.response.Response;

public class Auth extends ApiStellarburgers {
    public static final String URL_REGISTER = "/api/auth/register";
    public static final String URL_REGISTER_LOG = "/api/auth/login";
    public static final String URL_REGISTER_DEL = "/api/auth/user";

    //Создание пользователя
    public Response register(RegisterIn bodyJason) {
        return doPostRequest(URL_REGISTER , bodyJason,"");
    }

    //Авторизация пользователя
    public Response login(RegisterIn bodyJason) {
        return doPostRequest(URL_REGISTER_LOG , bodyJason,"");
    }

    // Удаление пользователя
    public Response delete(String token) {
        return doDeleteRequest(URL_REGISTER_DEL, token);
    }

    // Изменение данных пользователя
    public Response patch(RegisterIn bodyJason, String token) {
        return doPatchRequest(URL_REGISTER_DEL, bodyJason, token);
    }
}
