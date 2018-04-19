package org.university.innopolis.server.services;

import org.university.innopolis.server.common.JsonSerializable;

public interface StateService {
    void subscribe(JsonSerializable object);
    void exportState(String path);
    void importState(String path);
}
