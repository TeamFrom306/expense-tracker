package org.university.innopolis.server.services.realization.mappers;

import org.university.innopolis.server.model.Holder;
import org.university.innopolis.server.views.HolderView;

public class HolderMapper {
    private HolderMapper() {}

    public static HolderView map(Holder holder) {
        return new HolderView(
                holder.getLogin(),
                holder.getToken());
    }
}
