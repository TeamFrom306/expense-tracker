package org.university.innopolis.server.common;

public interface JsonSerializable {
    void importFromJson(String json);
    String exportToJson();
}
