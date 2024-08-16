package base;

import java.util.Map;
import static constants.Url.URL_API;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public abstract class ApiStellarburgers {

    private RequestSpecification baseRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(URL_API)
                .addHeader("Content-type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();

    }
    protected Response doGetRequest(String path, String authorization){
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", authorization)
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body, String authorization){
        return  given()
                .spec(baseRequestSpec())
                .header("Authorization", authorization)
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doDeleteRequest(String path, String authorization ){
        return  given()
                .spec(baseRequestSpec())
                .header("Authorization", authorization)
                .delete(path)
                .thenReturn();
    }

    protected Response doPatchRequest(String path, Object body, String authorization){
        return  given()
                .spec(baseRequestSpec())
                .header("Authorization", authorization)
                .body(body)
                .patch(path)
                .thenReturn();
    }

}

