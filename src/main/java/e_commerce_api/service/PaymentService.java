package e_commerce_api.service;

import e_commerce_api.service.event.PaymentEventPublisher;
import e_commerce_api.service.strategy.PaymentStrategyFactory;
import e_commerce_api.service.strategy.PaymentMethodStrategy;
import e_commerce_api.service.util.CartCalculator;
import e_commerce_api.domain.model.Payment;
import e_commerce_api.domain.model.Product;
import e_commerce_api.infrastructure.api.dto.PaymentRequest;
import e_commerce_api.infrastructure.api.dto.PaymentResponse;
import e_commerce_api.infrastructure.repository.PaymentRepository;
import e_commerce_api.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CartCalculator cartCalculator;
    private final PaymentStrategyFactory strategyFactory;
    private final PaymentEventPublisher eventPublisher;
    private final ProductRepository productRepository;


    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {

        BigDecimal originalAmount = cartCalculator.calculateTotalAmount(request.getProducts());

        Payment payment = Payment.builder()
                .originalAmount(originalAmount)
                .appliedDiscount(BigDecimal.ZERO)
                .cashbackAmount(BigDecimal.ZERO)
                .finalAmount(originalAmount)
                .paymentMethod(request.getPaymentMethodType())
                .status("PROCESSED")
                .build();

        PaymentMethodStrategy strategy = strategyFactory.getStrategy(request.getPaymentMethodType());

        payment = strategy.applyRules(payment, originalAmount);
        payment = paymentRepository.save(payment);

        PaymentResponse response = buildResponse(payment);
        eventPublisher.publishPaymentProcessedEvent(response);

        return response;
    }

    private PaymentResponse buildResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .originalAmount(payment.getOriginalAmount())
                .appliedDiscount(payment.getAppliedDiscount())
                .finalAmount(payment.getFinalAmount())
                .cashbackAmount(payment.getCashbackAmount())
                .methodType(payment.getPaymentMethod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}