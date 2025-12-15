package e_commerce_api.service.strategy;

import e_commerce_api.domain.model.Payment;
import e_commerce_api.domain.enums.PaymentMethod;
import java.math.BigDecimal;

public interface PaymentMethodStrategy {

    PaymentMethod getMethodType();

    Payment applyRules(Payment payment, BigDecimal originalAmount);
}
