package org.university.innopolis.server.services.realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.model.Holder;
import org.university.innopolis.server.persistence.HolderRepository;
import org.university.innopolis.server.services.HolderService;
import org.university.innopolis.server.services.realization.mappers.HolderMapper;
import org.university.innopolis.server.views.HolderView;

@Service
public class HolderServiceImplementation implements HolderService {
    private HolderRepository holderRepository;

    @Autowired
    public HolderServiceImplementation(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    @Override
    public HolderView findHolder(String login) {
        Holder holder = holderRepository.getByLogin(login);
        if (holder == null)
            return null;
        else
            return HolderMapper.map(holder);
    }
}