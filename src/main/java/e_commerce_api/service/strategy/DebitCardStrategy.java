package e_commerce_api.service.strategy;

import e_commerce_api.domain.model.Payment;
import e_commerce_api.domain.enums.PaymentMethod;
import e_commerce_api.service.util.FinancialUtils;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DebitCardStrategy implements PaymentMethodStrategy {

    @Override
    public PaymentMethod getMethodType() {
        return PaymentMethod.DEBIT_CARD;
    }

    @Override
    public Payment applyRules(Payment payment, BigDecimal originalAmount) {

        return Payment.builder()
                .originalAmount(payment.getOriginalAmount())
                .finalAmount(FinancialUtils.ensureScaleAndNonNull(originalAmount))
                .appliedDiscount(FinancialUtils.ensureScaleAndNonNull(BigDecimal.ZERO))
                .cashbackAmount(FinancialUtils.ensureScaleAndNonNull(BigDecimal.ZERO))
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
    }
}
