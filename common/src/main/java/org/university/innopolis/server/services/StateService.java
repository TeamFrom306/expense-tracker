package org.university.innopolis.server.services;

public interface StateService {
    void exportState(String path);
    void importState(String path);
}
