package my.project.clientProcessing.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Autowired
    Environment environment;

    @Value("${spring.kafka.topics.client-products}")
    private String topicClientProducts;
    @Value("${spring.kafka.topics.client-cards}")
    private String topicCard;
    @Value("${spring.kafka.topics.client-credit-products}")
    private String topicClientCreditProducts;
    @Value("${spring.kafka.topics.client-transactions}")
    private String topicClientTransactions;
    @Value("${spring.kafka.topics.client-payments}")
    private String topicClientPayments;

    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.key-serializer"));
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.value-serializer"));
        config.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
        config.put(ProducerConfig.LINGER_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return config;
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

    @Bean
    NewTopic createClientProductTopic() {
        return TopicBuilder.name(topicClientProducts)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createCardTopic() {
        return TopicBuilder.name(topicCard)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createClientCreditProductsTopic() {
        return TopicBuilder.name(topicClientCreditProducts)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createClientTransactionsTopic() {
        return TopicBuilder.name(topicClientTransactions)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createClientPaymentsTopic() {
        return TopicBuilder.name(topicClientPayments)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
