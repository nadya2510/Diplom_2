package ingredients;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientsOut {
    private Boolean success;
    private List<DataIngredients> data;
}
