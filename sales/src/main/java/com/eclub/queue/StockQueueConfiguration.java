package com.eclub.queue;


import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockQueueConfiguration {
    @Value("${stock-queue.name}")
    private final String stockQueueName;
    @Value("${stock-queue.routing-key}")
    private final String stockQueueRoutingKey;

    @Bean
    public ConnectionNameStrategy connectionNameStrategy() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(stockQueueName)
                .deadLetterExchange(String.join(".", stockQueueName, "dlx"))
                .build();
    }

    @Bean
    public Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).with(stockQueueRoutingKey);
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }

    @Bean
    public MessageConverter messageConverter() {
        var converter = new Jackson2JsonMessageConverter();
        converter.setCreateMessageIds(true);
        return converter;
    }
}
