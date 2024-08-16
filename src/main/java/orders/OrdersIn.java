package orders;

import java.util.List;

public class OrdersIn {
    private List<String> ingredients;

    public OrdersIn(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrdersIn() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
