package register;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import java.util.Random;

public class UserTest {
    AuthStep authStep = new AuthStep();
    Integer random = new Random().nextInt();
    String email = String.format("test-data%d@yandex.ru", random);
    String password = "password123";
    String username = String.format("Username%d", random);
    RegisterIn json  = new RegisterIn(email, password, username);
    RegisterIn jsonLog ;

    @Test
    @DisplayName("200-Check user in login- email")
    @Description("Status code 200 test")
    public void checkLoginUserEmail() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //Меняем данные
        Integer newRandom = new Random().nextInt();
        email = String.format("test-data%d@yandex.ru", newRandom);
        RegisterIn jsonTest  = new RegisterIn(email, password, username);
        authStep.patchRequestUser(jsonTest, accessTokenLog);
        //проверим
        authStep.checkAuthRegisterCodeBody(200,true, "");
        //для удаления пользователя с измененными данными
        jsonLog = new RegisterIn(email, password, username, accessToken);

    }

    @Test
    @DisplayName("200-Check user in login- password")
    @Description("Status code 200 test")
    public void checkLoginUserPassword() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //Меняем данные
        Integer newRandom = new Random().nextInt();
        password = String.format("password%d", newRandom);
        RegisterIn jsonTest  = new RegisterIn(email, password, username);
        authStep.patchRequestUser(jsonTest, accessTokenLog);
        //проверим
        authStep.checkAuthRegisterCodeBody(200,true, "");
        //для удаления пользователя с измененными данными
        jsonLog = new RegisterIn(email, password, username, accessToken);
    }

    @Test
    @DisplayName("200-Check user in login- username")
    @Description("Status code 200 test")
    public void checkLoginUserUsername() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        //авторизация пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = authStep.postRequestAuthLogin(jsonLog);
        //Меняем данные
        Integer newRandom = new Random().nextInt();
        username = String.format("Username%d", newRandom);
        RegisterIn jsonTest  = new RegisterIn(email, password, username);
        authStep.patchRequestUser(jsonTest, accessTokenLog);
        //проверим
        authStep.checkAuthRegisterCodeBody(200,true, "");
        //для удаления пользователя с измененными данными
        jsonLog = new RegisterIn(email, password, username, accessToken);
    }

    @Test
    @DisplayName("200-Check user logout — username")
    @Description("Status code 401 test")
    public void checkLoginUserLogout() {
        // создать уникального пользователя
        String  accessToken = authStep.postRequestAuthRegister(json);
        jsonLog = new RegisterIn(email, password, username, accessToken);
        String accessTokenLog = "";
        //Меняем данные
        Integer newRandom = new Random().nextInt();
        email = String.format("test-data%d@yandex.ru", newRandom);
        password = String.format("password%d", newRandom);
        username = String.format("Username%d", newRandom);
        RegisterIn jsonTest  = new RegisterIn(email, password, username);
        authStep.patchRequestUser(jsonTest, accessTokenLog);
        //проверим
        authStep.checkAuthRegisterCodeBody(401,false, "You should be authorised");

    }

    @After
    public void deleteUser() {
        if (jsonLog != null) {
            String accessToken = authStep.postRequestAuthLogin(jsonLog);
            authStep.deleteUser(accessToken);
        }
    }
}
