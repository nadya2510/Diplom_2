package ingredients;

import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;


public class IngredientsStep {
    private Ingredients ingredients = new Ingredients();

    // Получение данных об ингредиентах
    @Step("Send Get list _id ingredients")
    public List<String> getRequestIngredientsList() {
        IngredientsOut ingredientsOut = ingredients.getListIngredients().body().as(IngredientsOut.class);
        List<String> listID = new ArrayList<>();
        List<DataIngredients> dataIngredients = ingredientsOut.getData();

        for (DataIngredients dataIngredient : dataIngredients){
            listID.add(dataIngredient.get_id());
        }
        return listID;



    }

}
