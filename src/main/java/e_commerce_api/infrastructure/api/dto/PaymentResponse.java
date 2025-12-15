package e_commerce_api.infrastructure.api.dto;

import e_commerce_api.domain.enums.PaymentMethod;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private UUID id;
    private BigDecimal originalAmount;
    private BigDecimal appliedDiscount;
    private BigDecimal finalAmount;
    private BigDecimal cashbackAmount;
    private PaymentMethod methodType;
    private String status;
    private LocalDateTime createdAt;
}
