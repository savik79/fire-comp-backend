package pl.jtrend.firecomp.mapper.helper;

import pl.jtrend.firecomp.entity.FireStation;
@org.mapstruct.Mapper(componentModel =  "spring")
public interface FireStationMapper {

    default Long map(FireStation fireStation) {
        return fireStation == null ? null : fireStation.getId();
    }
}
