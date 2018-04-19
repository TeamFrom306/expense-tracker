package org.university.innopolis.server.kafka.producer;

import org.springframework.stereotype.Component;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.kafka.common.KafkaRecord;

import java.util.Date;
import java.util.Random;

@Component
public class RecordGenerator {
    private static Random r = new Random();

    public KafkaRecord generateRecord() {
        double amount = r.nextDouble() * 1000;
        Type type = r.nextBoolean() ? Type.EXPENSE : Type.INCOME;

        return new KafkaRecord(
                "Description",
                amount,
                Currency.RUB,
                new Date(),
                type);
    }
}
