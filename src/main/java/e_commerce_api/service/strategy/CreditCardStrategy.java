package e_commerce_api.service.strategy;

import e_commerce_api.domain.model.Payment;
import e_commerce_api.domain.enums.PaymentMethod;
import e_commerce_api.service.util.FinancialUtils;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class CreditCardStrategy implements PaymentMethodStrategy {

    private static final BigDecimal CASHBACK_RATE = new BigDecimal("0.03");

    @Override
    public PaymentMethod getMethodType() {
        return PaymentMethod.CREDIT_CARD;
    }

    @Override
    public Payment applyRules(Payment payment, BigDecimal originalAmount) {
        BigDecimal cashback = originalAmount.multiply(CASHBACK_RATE);
        cashback = FinancialUtils.ensureScaleAndNonNull(cashback);

        return Payment.builder()
                .originalAmount(payment.getOriginalAmount())
                .cashbackAmount(cashback)
                .finalAmount(FinancialUtils.ensureScaleAndNonNull(originalAmount))
                .appliedDiscount(FinancialUtils.ensureScaleAndNonNull(BigDecimal.ZERO))
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
    }
}
