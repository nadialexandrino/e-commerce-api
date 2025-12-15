package e_commerce_api.service.strategy;

import e_commerce_api.domain.model.Payment;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardStrategyTest {

    private final CreditCardStrategy strategy = new CreditCardStrategy();
    private final BigDecimal originalAmount = new BigDecimal("1000.00");

    @Test
    void shouldApplyThreePercentCashbackAndKeepOriginalAmount() {

        Payment basePayment = Payment.builder().build();

        Payment resultPayment = strategy.applyRules(basePayment, originalAmount);

        assertEquals(new BigDecimal("30.00"), resultPayment.getCashbackAmount());
        assertEquals(originalAmount, resultPayment.getFinalAmount());
        assertEquals(BigDecimal.ZERO.setScale(2), resultPayment.getAppliedDiscount());
    }
}
