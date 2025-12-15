package e_commerce_api.service.event;

import e_commerce_api.infrastructure.api.dto.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {

    private static final String TOPIC_NAME = "payments-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishPaymentProcessedEvent(PaymentResponse response) {
        try {
            String message = objectMapper.writeValueAsString(response);

            kafkaTemplate.send(TOPIC_NAME, response.getId().toString(), message);
            log.info("Evento de pagamento enviado ao Kafka: {}", response.getId());

        } catch (Exception e) {
            log.error("Erro ao enviar mensagem ao Kafka", e);
        }
    }
}