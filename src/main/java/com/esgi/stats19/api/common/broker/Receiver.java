package com.esgi.stats19.api.common.broker;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RabbitListener(queues = "java_queue")
public class Receiver implements Serializable {

    private final Sender sender;

    @Autowired
    public Receiver(Sender sender) {
        this.sender = sender;
    }


    @RabbitHandler
    public void receiveMessage(byte[] message) {
        try {
            JSONObject returnMessage = new JSONObject(new String(message));
            System.out.println("Received <" + returnMessage + ">");
//            this.sender.send("predict","DEVELOPMENT","TRUE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}