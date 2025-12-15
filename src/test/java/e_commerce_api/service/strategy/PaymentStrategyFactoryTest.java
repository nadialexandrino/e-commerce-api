package e_commerce_api.service.strategy;

import e_commerce_api.domain.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStrategyFactoryTest {

    private PaymentStrategyFactory factory;

    private final PixStrategy pixStrategy = new PixStrategy();
    private final CreditCardStrategy creditCardStrategy = new CreditCardStrategy();
    private final DebitCardStrategy debitCardStrategy = new DebitCardStrategy();

    @BeforeEach
    void setUp() {
        List<PaymentMethodStrategy> strategies = Arrays.asList(
                pixStrategy, creditCardStrategy, debitCardStrategy
        );

        factory = new PaymentStrategyFactory(strategies);
    }

    @Test
    void shouldRetrievePixStrategy() {
        PaymentMethodStrategy strategy = factory.getStrategy(PaymentMethod.PIX);
        assertTrue(strategy instanceof PixStrategy);
    }

    @Test
    void shouldThrowExceptionForUnsupportedMethod() {

        assertThrows(IllegalArgumentException.class, () -> {
            factory.getStrategy(null);
        });
    }
}
