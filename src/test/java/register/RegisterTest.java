package register;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import java.util.Random;

public class RegisterTest {
    AuthStep authStep = new AuthStep();
    Integer random = new Random().nextInt();
    String email = String.format("test-data%d@yandex.ru", random);
    String password = "password123";
    String username = String.format("Username%d", random);
    RegisterIn json  = new RegisterIn(email, password, username);
    RegisterIn jsonLog ;


    //создать уникального пользователя
    @Test
    @DisplayName("200-Check add register user")
    @Description("Status code 200 test")
    public void checkRegisterAddUser() {
        String  accessToken = authStep.postRequestAuthRegister(json);
        authStep.checkAuthRegisterCodeBody(200,true, "");
        //Для авторизации и удаления пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
    }

    //Если пользователь существует, вернётся код ответа 403 Forbidden
    @Test
    @DisplayName("403-Check add register user already exists")
    @Description("Status code 403 test")
    public void checkRegisterAddUserDuplicate() {
        //создали уникального
        String  accessToken = authStep.postRequestAuthRegister(json);
        //создаем повторно
        authStep.postRequestAuthRegister(json);
        authStep.checkAuthRegisterCodeBody(403,false, "User already exists");
        //Для авторизации и удаления пользователя
        jsonLog = new RegisterIn(email, password, username, accessToken);
    }

    //Если нет одного из полей, вернётся код ответа 403 Forbidden.
    @Test
    @DisplayName("403-Check add register user username absent")
    @Description("Status code 403 test")
    public void checkRegisterAddUserUsernameAbsente() {
        //создали без username
        RegisterIn jsonTest  = new RegisterIn(email, password,null);
        authStep.postRequestAuthRegister(jsonTest);
        authStep.checkAuthRegisterCodeBody(403,false, "Email, password and name are required fields");
    }

    //Если нет одного из полей, вернётся код ответа 403 Forbidden.
    @Test
    @DisplayName("403-Check add register user password absent")
    @Description("Status code 403 test")
    public void checkRegisterAddUserPasswordAbsente() {
        //создали без username
        RegisterIn jsonTest  = new RegisterIn(email, null, username);
        authStep.postRequestAuthRegister(jsonTest);
        authStep.checkAuthRegisterCodeBody(403,false, "Email, password and name are required fields");
    }

    //Если нет одного из полей, вернётся код ответа 403 Forbidden.
    @Test
    @DisplayName("403-Check add register user email absent")
    @Description("Status code 403 test")
    public void checkRegisterAddUserEmailAbsente() {
        //создали без username
        RegisterIn jsonTest  = new RegisterIn(null, password, username);
        authStep.postRequestAuthRegister(jsonTest);
        authStep.checkAuthRegisterCodeBody(403,false, "Email, password and name are required fields");
    }



    @After
    public void deleteUser() {
        if (jsonLog != null) {
            String accessToken = authStep.postRequestAuthLogin(jsonLog);
            authStep.deleteUser(accessToken);
        }
    }
}
