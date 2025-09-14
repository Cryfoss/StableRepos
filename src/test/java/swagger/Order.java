package swagger;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class Order {
        private Integer id;
        private Integer petId;
        private Integer quantity;
        private Date shipDate;
        private String status;
        private boolean complete;
    }

