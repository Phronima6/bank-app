package com.bankapp.exchange;

import com.bankapp.exchange.dto.UpdateRandomCurrencyDto;
import com.bankapp.exchange.service.RateService;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ExchangeApplicationTests extends SpringBootPostgreSQLTestContainerBaseTest {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@MockitoSpyBean
	private RateService rateService;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
	@Order(2)
	void kafkaTest() throws JsonProcessingException, ExecutionException, InterruptedException {
		UpdateRandomCurrencyDto dto = new UpdateRandomCurrencyDto();
		dto.setValue(1);
		kafkaTemplate.send(
				"exchange-generator",
				UUID.randomUUID().toString(),
				new ObjectMapper().writeValueAsString(dto)
		).get();
		Awaitility.await().atMost(Duration.ofSeconds(5)).untilAsserted(() ->
				Mockito.verify(rateService, Mockito.times(1)).applyRandomCurrencyUpdate(Mockito.any(UpdateRandomCurrencyDto.class))
		);
	}

}