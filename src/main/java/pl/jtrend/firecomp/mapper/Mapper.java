package pl.jtrend.firecomp.mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.*;
import pl.jtrend.firecomp.dto.*;
import pl.jtrend.firecomp.entity.*;

@org.mapstruct.Mapper(componentModel =  "spring")
public interface Mapper {

    Mapper INSTANCE = Mappers.getMapper(Mapper.class);


    // === User ===
    UserDto toUserDto(User user);
    User toUser(UserDto dto);

    // === Event ===
    @Mapping(target = "participantIds", source = "participants")
    EventDto toEventDto(Event event);
    @Mapping(target = "participants", ignore = true) // handled manually
    Event toEvent(EventDto dto);

    // === Fireman ===
    @Mapping(target = "fireStation", ignore = true) // handled in service
    FiremanDto toFiremanDto(Fireman fireman);
    @Mapping(target = "fireStation", ignore = true)
    Fireman toFireman(FiremanDto dto);

    // === FireStation ===
    FireStationDto toFireStationDto(FireStation station);
    FireStation toFireStation(FireStationDto dto);

    // === FireTruck ===
    @Mapping(target = "station", ignore = true)
    FireTruckDto toFireTruckDto(FireTruck truck);
    @Mapping(target = "station", ignore = true)
    FireTruck toFireTruck(FireTruckDto dto);

    // === Report ===
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "event", ignore = true)
    ReportDto toReportDto(Report report);
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "event", ignore = true)
    Report toReport(ReportDto dto);
}
