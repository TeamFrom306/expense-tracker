package org.university.innopolis.server.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.kafka.common.KafkaRecord;
import org.university.innopolis.server.services.AddRecordService;
import org.university.innopolis.server.services.exceptions.WrongAmountValueException;
import org.university.innopolis.server.services.exceptions.WrongDateParameterException;

@Component
public class KafkaController {
    private AddRecordService addRecordService;

    public static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    public KafkaController(AddRecordService addRecordService) {
        this.addRecordService = addRecordService;
    }

    public void addRecord(KafkaRecord record, int accountId) {
        try {
            addRecordService.addRecord(
                    record.getDescription(),
                    record.getAmount(),
                    record.getCurrency(),
                    record.getDate().getTime() / 1000,
                    record.getType(),
                    accountId);
        } catch (WrongAmountValueException | WrongDateParameterException ignored) {
            logger.error(ignored.toString());
        }
    }
}
