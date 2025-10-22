package pl.jtrend.firecomp.mapper.helper;

import pl.jtrend.firecomp.entity.Event;
import pl.jtrend.firecomp.entity.Fireman;

@org.mapstruct.Mapper(componentModel =  "spring")
public interface EventMapper {

    default Long map(Event event) {
        return event == null ? null : event.getId();
    }
}
