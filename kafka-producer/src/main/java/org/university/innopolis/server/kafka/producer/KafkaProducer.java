package org.university.innopolis.server.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.kafka.common.KafkaRecord;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class KafkaProducer implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducer.class, args).close();
    }

    @Autowired
    private KafkaTemplate<Integer, KafkaRecord> template;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            KafkaRecord record = new KafkaRecord(
                    "descr",
                    new Random().nextDouble() * 1000,
                    Currency.DOLLAR,
                    new Date(),
                    Type.EXPENSE);
            template.send("record", 1, record);
            template.flush();
            Thread.sleep(200);
        }
        logger.info("Done");
    }
}
