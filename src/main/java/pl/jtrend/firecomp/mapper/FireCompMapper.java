package pl.jtrend.firecomp.mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.*;
import pl.jtrend.firecomp.dto.*;
import pl.jtrend.firecomp.entity.*;
import pl.jtrend.firecomp.mapper.helper.*;

@org.mapstruct.Mapper(componentModel =  "spring",
        uses = {FiremanMapper.class, AddressMapper.class,
                FireTruckMapper.class, FireStationMapper.class,
                EventMapper.class, UserMapper.class})
public interface FireCompMapper {

    FireCompMapper INSTANCE = Mappers.getMapper(FireCompMapper.class);


    // === User ===
    UserDto toUserDto(User user);
    User toUser(UserDto dto);

    // === Event ===
    @Mapping(target = "participantIds", source = "participants")
    EventDto toEventDto(Event event);
    @Mapping(target = "participants", ignore = true) // handled manually
    Event toEvent(EventDto dto);

    // === Fireman ===
    FiremanDto toFiremanDto(Fireman fireman);
    @Mapping(target = "fireStation", ignore = true)
    Fireman toFireman(FiremanDto dto);

    // === FireStation ===
    FireStationDto toFireStationDto(FireStation station);
    FireStation toFireStation(FireStationDto dto);

    @Mapping(source = "station", target = "stationId")
    FireTruckDto toFireTruckDto(FireTruck truck);

    // For toEntity, we usually ignore station and event; set them in service
    @Mapping(target = "station", ignore = true)
    @Mapping(target = "event", ignore = true)
    FireTruck toFireTruck(FireTruckDto dto);

    // === Report ===
    @Mapping(target = "eventId", source = "event")
    @Mapping(target = "userId", source = "user")
    ReportDto toReportDto(Report report);
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "event", ignore = true)
    Report toReport(ReportDto dto);
}
