package com.bankapp.exchangegenerator;

import com.bankapp.exchangegenerator.service.UpdateRandomCurrencyService;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestOAuth2Config.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ExchangeGeneratorApplicationTests {

	@Autowired
	private UpdateRandomCurrencyService updateRandomCurrencyService;
	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
	@Order(2)
	void kafkaTest() {
		try (var consumerForTest = new DefaultKafkaConsumerFactory<>(
				KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker),
				new StringDeserializer(),
				new StringDeserializer()
		).createConsumer()) {
			consumerForTest.subscribe(List.of("exchange-generator"));
			updateRandomCurrencyService.update();
			ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumerForTest);
			for (ConsumerRecord<String, String> record : records) {
				Assertions.assertNotNull(record.key());
				Assertions.assertNotNull(record.value());
			}
		}
	}

}