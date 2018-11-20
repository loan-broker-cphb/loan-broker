package com.loanbroker.bank.translator.xml;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.loanbroker.commons.model.BankMessage;
import com.loanbroker.commons.model.RabbitTemplateBuilder;
import com.loanbroker.utils.DateUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TranslatorXmlApplication {

    private static final String queueName = "bank.xml.translator";
    private static final String exchangeName = "translator.exch";
    private static final String bankUri = "amqp://guest:guest@datdb.cphbusiness.dk:5672";
    private final String bankExchange = "cphbusiness.bankXML";
    private final String routingKey = "CPHB_XML";


    @Bean
    RabbitTemplate rabbitTemplate(MessageConverter xmlConverter) {
        return RabbitTemplateBuilder.newBuilder()
                .connectionUri(bankUri)
                .messageConverter(xmlConverter)
                .exchange(bankExchange)
                .build();
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.addQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    XmlMapper xmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(DateUtil.dateFormat());
        return mapper;
    }

    @Bean
    MessageConverter xmlConverter(XmlMapper xmlMapper) {
        return new Jackson2XmlMessageConverter(xmlMapper);
    }

    @Bean
    ClassMapper bankMessageClassMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(BankMessage.class);
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.loanbroker.commons.model.BankMessage", BankMessage.class);
        mapper.setIdClassMapping(idClassMapping);
        return mapper;
    }

    @Bean
    MessageConverter fromXmlConverter() {
        Jackson2XmlMessageConverter converter = new Jackson2XmlMessageConverter();
        converter.setClassMapper(bankMessageClassMapper());
        return converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(TranslatorXmlApplication.class, args);
    }
}