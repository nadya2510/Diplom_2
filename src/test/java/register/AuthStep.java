package register;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;


public class AuthStep {
    private Auth auth = new Auth();
    private ValidatableResponse response;
    private String accessTokenRegister;
    private String accessTokenLogin;

    // Создание пользователя
    @Step("Send Post add register user request")
    public String postRequestAuthRegister(RegisterIn bodyJason) {
        response = auth.register(bodyJason).then();
        accessTokenRegister = response.extract().body().path("accessToken");
        return accessTokenRegister;
    }

    // Проверяем пользователя по коду и тело
    @Step("Check user: code, body")
    public void checkAuthRegisterCodeBody(Integer statusCode, Boolean success, String message) {
        response.assertThat().statusCode(statusCode);
        Boolean responseSuccess =  response.extract().body().path("success");
        MatcherAssert.assertThat(responseSuccess,is(success));
        if (message != ""){
            String responseMessage =  response.extract().body().path("message");
            MatcherAssert.assertThat(responseMessage, equalTo(message));
        }
    }

    // Авторизация пользователя
    @Step("Send Post login user request")
    public String postRequestAuthLogin(RegisterIn bodyJason) {
        response = auth.login(bodyJason).then();
        accessTokenLogin = response.extract().body().path("accessToken");
        return accessTokenLogin;
    }
    // Удаляем пользователя
    @Step("Send Delete user request")
    public void deleteUser(String token) {
        response = auth.delete(token).then();
    }

    // Изменение данных пользователя
    @Step("Send Putch user request")
    public void patchRequestUser(RegisterIn bodyJason,String token) {
        response = auth.patch(bodyJason, token).then();
    }

}
