package orders;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersList {
    private List<String> ingredients;
    private String _id;
    private String status;
    private Integer number;
    private String createdAt;
    private String updatedAt;

}
