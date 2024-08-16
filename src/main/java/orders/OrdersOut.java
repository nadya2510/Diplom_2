package orders;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersOut {
    private Boolean success;
    private List<OrdersList> orders;
    private Integer total;
    private Integer totalToday;

}
