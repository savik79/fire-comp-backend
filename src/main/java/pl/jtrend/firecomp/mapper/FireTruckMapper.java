package pl.jtrend.firecomp.mapper;

import pl.jtrend.firecomp.entity.FireTruck;
@org.mapstruct.Mapper(componentModel =  "spring")
public interface FireTruckMapper {
    default Long map(FireTruck fireTruck) {
        return fireTruck == null ? null : fireTruck.getId();
    }
}
