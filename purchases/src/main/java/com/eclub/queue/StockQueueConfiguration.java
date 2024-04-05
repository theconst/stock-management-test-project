package com.eclub.queue;


import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
    private final String purchaseQueueName;
    @Value("${stock-queue.routing-key}")
    private final String purchaseQueueRoutingKey;

    @Bean
    public ConnectionNameStrategy connectionNameStrategy() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

    @Bean
    public Queue queue() {
        return new Queue(purchaseQueueName, true);
    }

    @Bean
    public Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).with(purchaseQueueRoutingKey);
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}