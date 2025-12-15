package e_commerce_api.service.util;

import e_commerce_api.domain.model.Product;
import e_commerce_api.infrastructure.api.dto.PaymentRequest;
import e_commerce_api.infrastructure.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartCalculatorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartCalculator cartCalculator;

    private final UUID productId1 = UUID.randomUUID();
    private final UUID productId2 = UUID.randomUUID();
    private final Product product1 = Product.builder().id(productId1).price(new BigDecimal("100.00")).build();
    private final Product product2 = Product.builder().id(productId2).price(new BigDecimal("50.00")).build();

    @Test
    void shouldCalculateTotalAmountCorrectly() {

        PaymentRequest.ProductItem item1 = new PaymentRequest.ProductItem(productId1, 2);
        PaymentRequest.ProductItem item2 = new PaymentRequest.ProductItem(productId2, 3);
        List<PaymentRequest.ProductItem> items = Arrays.asList(item1, item2);

        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));

        BigDecimal total = cartCalculator.calculateTotalAmount(items);

        assertEquals(new BigDecimal("350.00"), total);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        PaymentRequest.ProductItem item = new PaymentRequest.ProductItem(productId1, 1);
        List<PaymentRequest.ProductItem> items = List.of(item);

        when(productRepository.findById(productId1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartCalculator.calculateTotalAmount(items));
    }
}
