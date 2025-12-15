package e_commerce_api.infrastructure.api.dto;
import e_commerce_api.domain.enums.PaymentMethod;
import lombok.*;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private List<ProductItem> products;
    private PaymentMethod paymentMethodType;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductItem {

        private UUID product_id;
        private Integer quantity;
    }
}
