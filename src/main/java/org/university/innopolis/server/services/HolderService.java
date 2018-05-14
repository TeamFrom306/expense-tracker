package org.university.innopolis.server.services;

import org.university.innopolis.server.views.HolderView;

public interface HolderService {
    HolderView findHolder(String login);

    HolderView adjustBalance(double newBalance, int holderId);
}
