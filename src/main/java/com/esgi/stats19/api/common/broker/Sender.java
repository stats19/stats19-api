package com.esgi.stats19.api.common.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Sender {

    private final RabbitTemplate template;

    @Autowired
    public Sender(RabbitTemplate template) {
        this.template = template;
    }

    public void send(String process, String environment, String force ) {
        String message = Message.builder().environment(environment).force(force).process(process).build().toString();
        this.template.convertAndSend("python_queue", message);
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
