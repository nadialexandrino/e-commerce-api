package e_commerce_api.service.util;

import e_commerce_api.domain.model.Product;
import e_commerce_api.infrastructure.api.dto.PaymentRequest;
import e_commerce_api.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartCalculator {

    private final ProductRepository productRepository;

    public BigDecimal calculateTotalAmount(List<PaymentRequest.ProductItem> items) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PaymentRequest.ProductItem item : items) {
            Product product = productRepository.findById(item.getProduct_id())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProduct_id()));

            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }
        return totalAmount;
    }
}
