package e_commerce_api.infrastructure.repository;

import e_commerce_api.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {}
