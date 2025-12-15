package e_commerce_api.infrastructure.api;

import e_commerce_api.domain.model.Product;
import e_commerce_api.infrastructure.api.dto.PaymentRequest;
import e_commerce_api.infrastructure.api.dto.PaymentResponse;
import e_commerce_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(paymentService.getAllProducts());
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }
}
