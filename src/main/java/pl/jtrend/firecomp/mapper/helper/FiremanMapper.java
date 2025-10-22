package pl.jtrend.firecomp.mapper.helper;

import pl.jtrend.firecomp.entity.Fireman;

@org.mapstruct.Mapper(componentModel =  "spring")
public interface FiremanMapper {

    default Long map(Fireman fireman) {
        return fireman == null ? null : fireman.getId();
    }
}
