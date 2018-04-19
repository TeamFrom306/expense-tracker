package org.university.innopolis.server.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.university.innopolis.server.kafka.common.KafkaRecord;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackages = "org.university.innopolis.server.services")
public class KafkaConsumer implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private KafkaController recordController;

    @Autowired
    public KafkaConsumer(KafkaController recordController) {
        this.recordController = recordController;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumer.class, args).close();
    }

    private final CountDownLatch latch = new CountDownLatch(1000);

    @KafkaListener(id = "kafkaListener", topics = "record")
    public void listen1(@Payload KafkaRecord record,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer accountId) {
        logger.info(record.toString());
        recordController.addRecord(record, accountId);

        this.latch.countDown();
    }

    @Override
    public void run(String... args) throws Exception {
        latch.await(600, TimeUnit.SECONDS);
    }
}
