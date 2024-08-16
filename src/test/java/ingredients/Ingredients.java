package ingredients;

import base.ApiStellarburgers;
import io.restassured.response.Response;

public class Ingredients extends ApiStellarburgers{
        public static final String URL_INDREDIENTS = "/api/ingredients";


        //Получение данных об ингредиентах
        public Response getListIngredients () {
            return doGetRequest(URL_INDREDIENTS , "");
        }
    }


