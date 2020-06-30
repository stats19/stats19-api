package com.esgi.stats19.api.common.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Sender {

    private final RabbitTemplate template;

    private String queue;

    @Autowired
    public Sender(@Value("${receiver.rabbitmq.queue}")String queue,
                  RabbitTemplate template) {
        this.template = template;
        this.queue = queue;
    }

    public void send(String process, String environment, String force ) {
        String message = Message.builder().environment(environment).force(force).process(process).build().toString();
        this.template.convertAndSend(queue, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}

@Data
@AllArgsConstructor
@Builder
class Message implements Serializable{
    public String process;
    public String environment;
    public String force;

    @Override
    public String toString() {
        return "{" +
                "\"process\":\"" + process + '\"' +
                ", \"environment\":\"" + environment + '\"' +
                ", \"force\":\"" + force + '\"' +
                '}';
    }
}
