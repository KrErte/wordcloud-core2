package ee.bitweb.wordcloud_core2.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${wordcloud.rabbitmq.exchange}")
    private String exchange;

    @Value("${wordcloud.rabbitmq.queue}")
    private String queue;

    @Value("${wordcloud.rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public DirectExchange wordcloudExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue wordcloudQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding wordcloudBinding() {
        return BindingBuilder.bind(wordcloudQueue()).to(wordcloudExchange()).with(routingKey);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationRunner rabbitAdminRunner(RabbitAdmin rabbitAdmin) {
        return args -> rabbitAdmin.initialize();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
