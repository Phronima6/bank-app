package com.bankapp.accounts;

import com.bankapp.accounts.feign.notifications.NotificationCreateDto;
import com.bankapp.accounts.service.NotificationKafkaService;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import java.time.Duration;
import java.util.List;

class AccountsApplicationTests extends SpringBootPostgreSQLTestContainerBaseTest {

	@Autowired
	private NotificationKafkaService notificationKafkaService;
	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
	@Order(2)
	void kafkaTest() {
		NotificationCreateDto dto = new NotificationCreateDto();
		dto.setMessage("message");
		dto.setLogin("login");
		dto.setSourceId(1L);
		try (var consumerForTest = new DefaultKafkaConsumerFactory<>(
				KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker),
				new StringDeserializer(),
				new StringDeserializer()
		).createConsumer()) {
			consumerForTest.subscribe(List.of("notifications"));
			notificationKafkaService.create(dto);
			var inputMessage = KafkaTestUtils.getSingleRecord(consumerForTest, "notifications", Duration.ofSeconds(10));
			Assertions.assertNotNull(inputMessage.key());
			Assertions.assertNotNull(inputMessage.value());
		}
	}

}