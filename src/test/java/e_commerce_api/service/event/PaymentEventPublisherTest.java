package e_commerce_api.service.event;

import e_commerce_api.domain.enums.PaymentMethod;
import e_commerce_api.infrastructure.api.dto.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentEventPublisherTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaymentEventPublisher eventPublisher;

    @Test
    void shouldPublishEventToKafkaWithCorrectArguments() throws JsonProcessingException {

        UUID paymentId = UUID.randomUUID();
        PaymentResponse response = PaymentResponse.builder()
                .id(paymentId)
                .methodType(PaymentMethod.PIX)
                .originalAmount(new BigDecimal("100.00"))
                .build();
        String expectedJson = "{\"id\":\"" + paymentId.toString() + "\", \"status\":\"PROCESSED\"}";

        when(objectMapper.writeValueAsString(response)).thenReturn(expectedJson);

        eventPublisher.publishPaymentProcessedEvent(response);

        verify(kafkaTemplate).send(
                eq("payments-topic"),
                eq(paymentId.toString()),
                eq(expectedJson)
        );
    }
}
