package com.example.demo.api;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeApiController {

    @Autowired
    @Qualifier("topicsenderclient")
    private ServiceBusSenderClient topicSenderClient;
    @Autowired
    @Qualifier("queuesenderclient")
    private ServiceBusSenderClient queueSenderClient;

    @Autowired
    @Qualifier("serviceBusTopicProcessorClient")
    private ServiceBusProcessorClient topicProcessorClient;
    @Autowired
    @Qualifier("serviceBusQueueProcessorClient")
    private ServiceBusProcessorClient queueProcessorClient;

    @PostMapping("/topic/enviar")
    public ResponseEntity topicSend(@RequestBody String msg){
        System.out.println(msg);
        topicSenderClient.sendMessage(new ServiceBusMessage(msg));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/queue/enviar")
    public ResponseEntity queueSend(@RequestBody String msg){
        System.out.println(msg);
        queueSenderClient.sendMessage(new ServiceBusMessage(msg));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/topic/receber")
    public ResponseEntity buscarTopic(){
        topicProcessorClient.start();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/queue/receber")
    public ResponseEntity buscarQueue(){
        queueProcessorClient.start();
        return ResponseEntity.ok().build();
    }
}
