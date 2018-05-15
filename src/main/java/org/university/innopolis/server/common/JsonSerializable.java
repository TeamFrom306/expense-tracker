package org.university.innopolis.server.common;

/**
 * Interface for serializing calculators
 * Both methods are dependent on each other
 *
 * Object should be able to import itself state by {@link #importFromJson(String)}
 * from json which was exported by {@link #exportToJson()}
 */
public interface JsonSerializable {

    /**
     * Import internal state from json
     * @param json String representation of internal state
     */
    void importFromJson(String json);

    /**
     * Export internal state to json string
     * @return String representation of internal state
     */
    String exportToJson();
}
