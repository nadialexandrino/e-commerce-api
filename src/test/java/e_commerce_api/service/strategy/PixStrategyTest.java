package e_commerce_api.service.strategy;

import e_commerce_api.domain.model.Payment;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PixStrategyTest {

    private final PixStrategy strategy = new PixStrategy();
    private final BigDecimal originalAmount = new BigDecimal("1000.00");

    @Test
    void shouldApplyFivePercentDiscount() {

        Payment basePayment = Payment.builder().build();

        Payment resultPayment = strategy.applyRules(basePayment, originalAmount);

        assertEquals(new BigDecimal("50.00"), resultPayment.getAppliedDiscount());
        assertEquals(new BigDecimal("950.00"), resultPayment.getFinalAmount());
        assertEquals(BigDecimal.ZERO.setScale(2), resultPayment.getCashbackAmount());
    }
}