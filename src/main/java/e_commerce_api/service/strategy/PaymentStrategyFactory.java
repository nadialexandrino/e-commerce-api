package e_commerce_api.service.strategy;

import e_commerce_api.domain.enums.PaymentMethod;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyFactory {

    private final Map<PaymentMethod, PaymentMethodStrategy> strategies;

    public PaymentStrategyFactory(List<PaymentMethodStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(PaymentMethodStrategy::getMethodType, Function.identity()));
    }

    public PaymentMethodStrategy getStrategy(PaymentMethod methodType) {
        PaymentMethodStrategy strategy = strategies.get(methodType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + methodType);
        }
        return strategy;
    }
}
