package e_commerce_api.infrastructure.repository;

import e_commerce_api.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
