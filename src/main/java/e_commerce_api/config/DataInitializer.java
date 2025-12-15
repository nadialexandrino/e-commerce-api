package e_commerce_api.config;

import e_commerce_api.domain.model.Product;
import e_commerce_api.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            productRepository.saveAll(Arrays.asList(
                    Product.builder().name("Smartphone Samsung").price(new BigDecimal("2500.00")).build(),
                    Product.builder().name("Notebook Dell").price(new BigDecimal("4500.00")).build(),
                    Product.builder().name("Monitor LG 24").price(new BigDecimal("900.00")).build(),
                    Product.builder().name("Teclado Mecânico").price(new BigDecimal("350.00")).build(),
                    Product.builder().name("Mouse Gamer").price(new BigDecimal("150.00")).build()
            ));
        }
    }
}
