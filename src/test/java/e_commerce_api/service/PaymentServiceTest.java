package e_commerce_api.service;

import e_commerce_api.service.event.PaymentEventPublisher;
import e_commerce_api.service.strategy.PaymentMethodStrategy;
import e_commerce_api.service.strategy.PaymentStrategyFactory;
import e_commerce_api.service.util.CartCalculator;
import e_commerce_api.domain.enums.PaymentMethod;
import e_commerce_api.domain.model.Payment;
import e_commerce_api.infrastructure.api.dto.PaymentRequest;
import e_commerce_api.infrastructure.api.dto.PaymentResponse;
import e_commerce_api.infrastructure.repository.PaymentRepository;
import e_commerce_api.infrastructure.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock private PaymentRepository paymentRepository;
    @Mock private CartCalculator cartCalculator;
    @Mock private PaymentStrategyFactory strategyFactory;
    @Mock private PaymentEventPublisher eventPublisher;
    @Mock private PaymentMethodStrategy mockStrategy;
    @Mock private ProductRepository productRepository;

    @InjectMocks
    private PaymentService paymentService;

    private final UUID paymentId = UUID.randomUUID();
    private final BigDecimal originalAmount = new BigDecimal("500.00");

    @Test
    void processPaymentShouldCoordinateAllSteps() {

        PaymentRequest request = new PaymentRequest(List.of(), PaymentMethod.PIX);

        when(cartCalculator.calculateTotalAmount(request.getProducts())).thenReturn(originalAmount);
        when(strategyFactory.getStrategy(PaymentMethod.PIX)).thenReturn(mockStrategy);

        Payment paymentAfterStrategy = Payment.builder()
                .originalAmount(originalAmount)
                .finalAmount(new BigDecimal("450.00"))
                .paymentMethod(PaymentMethod.PIX)
                .status("PROCESSED")
                .build();
        when(mockStrategy.applyRules(any(Payment.class), eq(originalAmount))).thenReturn(paymentAfterStrategy);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment paymentToSave = invocation.getArgument(0);
            paymentToSave.setId(paymentId);
            return paymentToSave;
        });

        PaymentResponse response = paymentService.processPayment(request);

        assertEquals(paymentId, response.getId());
        assertEquals(new BigDecimal("450.00"), response.getFinalAmount());

        verify(cartCalculator, times(1)).calculateTotalAmount(request.getProducts());
        verify(strategyFactory, times(1)).getStrategy(PaymentMethod.PIX);
        verify(mockStrategy, times(1)).applyRules(any(Payment.class), eq(originalAmount));

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());
        assertEquals(new BigDecimal("450.00"), paymentCaptor.getValue().getFinalAmount());

        verify(eventPublisher, times(1)).publishPaymentProcessedEvent(any(PaymentResponse.class));
    }

    @Test
    void getAllProductsShouldSimplyDelegateToRepository() {
        paymentService.getAllProducts();
        verify(productRepository, times(1)).findAll();
    }
}
