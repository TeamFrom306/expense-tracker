package org.university.innopolis.server.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.common.JsonSerializable;
import org.university.innopolis.server.services.StateService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for import and export internal states of the Calculators
 */
@Service
public class StateManager implements StateService {
    private static Map<Class, JsonSerializable> subscribers = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(StateManager.class);

    /**
     * Subscribe object to the export and import processes
     *
     * @param object to subscribe
     */
    @Override
    public void subscribe(JsonSerializable object) {
        subscribers.put(object.getClass(), object);
    }

    /**
     * Export calculators' states to the file with certain path
     * File will be created or overwritten
     *
     * @param path to destination of the exported file
     */
    @Override
    public void exportState(String path) {
        Map<Class, String> objectMap = new HashMap<>();
        for (JsonSerializable subscriber : subscribers.values()) {
            String json = subscriber.exportToJson();
            if (json != null)
                objectMap.put(subscriber.getClass(), json);
        }

        try (FileWriter writer = new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, objectMap);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Import calculators' states from the file
     *
     * @param path to file for import
     */
    @Override
    public void importState(String path) {
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        try {
            TypeReference<HashMap<Class, String>> typeRef
                    = new TypeReference<HashMap<Class, String>>() {};
            Map<Class, String> map = mapper.readValue(file, typeRef);
            for (Map.Entry<Class, String> entry : map.entrySet()) {
                subscribers.get(entry.getKey()).importFromJson(entry.getValue());
            }
            logger.info("Import from {} done", path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
