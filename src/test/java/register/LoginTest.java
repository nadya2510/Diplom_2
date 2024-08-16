package register;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import java.util.Random;

public class LoginTest {
    AuthStep authStep = new AuthStep();
    Integer random = new Random().nextInt();
    String email = String.format("test-data%d@yandex.ru", random);
    String password = "password123";
    String username = String.format("Username%d", random);
    RegisterIn json  = new RegisterIn(email, password, username);
    RegisterIn jsonLog ;



    @Test
    @DisplayName("200-Check login user")
    @Description("Status code 200 test")
    public void checkLoginUser() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        authStep.checkAuthRegisterCodeBody(200,true, "");

    }

    @Test
    @DisplayName("401-Check login user")
    @Description("Status code 401 test")
    public void checkLoginUserEmail() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        jsonLog = new RegisterIn(email, password, username, accessToken);

        //авторизация с неверным логином
        RegisterIn jsonTest  = new RegisterIn("test-data@yandex.ru", password, username);
        authStep.postRequestAuthLogin(jsonTest);
        authStep.checkAuthRegisterCodeBody(401,false, "email or password are incorrect");
    }

    @Test
    @DisplayName("401-Check login user")
    @Description("Status code 401 test")
    public void checkLoginUserPassword() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        jsonLog = new RegisterIn(email, password, username, accessToken);

        //авторизация с неверным логином
        RegisterIn jsonTest  = new RegisterIn(email, "123", username);
        authStep.postRequestAuthLogin(jsonTest);
        authStep.checkAuthRegisterCodeBody(401,false, "email or password are incorrect");
    }

    @After
    public void deleteUser() {
        if (jsonLog != null) {
            String accessToken = authStep.postRequestAuthLogin(jsonLog);
            authStep.deleteUser(accessToken);
        }
    }
}
