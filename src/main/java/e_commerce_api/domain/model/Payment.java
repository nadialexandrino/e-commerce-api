package e_commerce_api.domain.model;

import e_commerce_api.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal originalAmount;
    private BigDecimal appliedDiscount;
    private BigDecimal finalAmount;
    private BigDecimal cashbackAmount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
