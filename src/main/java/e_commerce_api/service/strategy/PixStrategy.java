package e_commerce_api.service.strategy;

import e_commerce_api.service.util.FinancialUtils;
import e_commerce_api.domain.model.Payment;
import e_commerce_api.domain.enums.PaymentMethod;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PixStrategy implements PaymentMethodStrategy {

    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.05");

    @Override
    public PaymentMethod getMethodType() {
        return PaymentMethod.PIX;
    }

    @Override
    public Payment applyRules(Payment payment, BigDecimal originalAmount) {

        BigDecimal discount = originalAmount.multiply(DISCOUNT_RATE);
        discount = FinancialUtils.ensureScaleAndNonNull(discount);

        BigDecimal finalAmount = originalAmount.subtract(discount);
        finalAmount = FinancialUtils.ensureScaleAndNonNull(finalAmount);

        return Payment.builder()
                .originalAmount(payment.getOriginalAmount())
                .appliedDiscount(discount)
                .finalAmount(finalAmount)
                .cashbackAmount(FinancialUtils.ensureScaleAndNonNull(BigDecimal.ZERO))
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
    }
}
