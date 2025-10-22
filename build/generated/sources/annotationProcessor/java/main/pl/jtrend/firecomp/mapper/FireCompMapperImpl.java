package pl.jtrend.firecomp.mapper;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jtrend.firecomp.dto.EventDto;
import pl.jtrend.firecomp.dto.FireStationDto;
import pl.jtrend.firecomp.dto.FireTruckDto;
import pl.jtrend.firecomp.dto.FiremanDto;
import pl.jtrend.firecomp.dto.ReportDto;
import pl.jtrend.firecomp.dto.UserDto;
import pl.jtrend.firecomp.entity.Event;
import pl.jtrend.firecomp.entity.FireStation;
import pl.jtrend.firecomp.entity.FireTruck;
import pl.jtrend.firecomp.entity.Fireman;
import pl.jtrend.firecomp.entity.Report;
import pl.jtrend.firecomp.entity.ReportType;
import pl.jtrend.firecomp.entity.User;
import pl.jtrend.firecomp.mapper.helper.EventMapper;
import pl.jtrend.firecomp.mapper.helper.FireStationMapper;
import pl.jtrend.firecomp.mapper.helper.FiremanMapper;
import pl.jtrend.firecomp.mapper.helper.UserMapper;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-22T18:00:08+0200",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class FireCompMapperImpl implements FireCompMapper {

    @Autowired
    private FiremanMapper firemanMapper;
    @Autowired
    private FireStationMapper fireStationMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );
        userDto.setEmail( user.getEmail() );
        userDto.setEnabled( user.isEnabled() );
        Set<String> set = user.getRoles();
        if ( set != null ) {
            userDto.setRoles( new LinkedHashSet<String>( set ) );
        }

        return userDto;
    }

    @Override
    public User toUser(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.username( dto.getUsername() );
        user.email( dto.getEmail() );
        user.enabled( dto.isEnabled() );
        Set<String> set = dto.getRoles();
        if ( set != null ) {
            user.roles( new LinkedHashSet<String>( set ) );
        }

        return user.build();
    }

    @Override
    public EventDto toEventDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDto.EventDtoBuilder eventDto = EventDto.builder();

        eventDto.participantIds( firemanSetToLongSet( event.getParticipants() ) );
        eventDto.id( event.getId() );
        eventDto.name( event.getName() );
        eventDto.description( event.getDescription() );
        eventDto.place( event.getPlace() );
        eventDto.date( event.getDate() );
        eventDto.durationHours( event.getDurationHours() );
        eventDto.startTimestamp( event.getStartTimestamp() );
        eventDto.endTimestamp( event.getEndTimestamp() );
        eventDto.mileage( event.getMileage() );
        eventDto.fireUnits( event.getFireUnits() );

        return eventDto.build();
    }

    @Override
    public Event toEvent(EventDto dto) {
        if ( dto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.id( dto.getId() );
        event.name( dto.getName() );
        event.description( dto.getDescription() );
        event.place( dto.getPlace() );
        event.date( dto.getDate() );
        event.durationHours( dto.getDurationHours() );
        event.startTimestamp( dto.getStartTimestamp() );
        event.endTimestamp( dto.getEndTimestamp() );
        event.mileage( dto.getMileage() );
        event.fireUnits( dto.getFireUnits() );

        return event.build();
    }

    @Override
    public FiremanDto toFiremanDto(Fireman fireman) {
        if ( fireman == null ) {
            return null;
        }

        FiremanDto.FiremanDtoBuilder firemanDto = FiremanDto.builder();

        firemanDto.id( fireman.getId() );
        firemanDto.name( fireman.getName() );
        firemanDto.surname( fireman.getSurname() );
        firemanDto.idNumber( fireman.getIdNumber() );
        firemanDto.bankAccount( fireman.getBankAccount() );

        return firemanDto.build();
    }

    @Override
    public Fireman toFireman(FiremanDto dto) {
        if ( dto == null ) {
            return null;
        }

        Fireman.FiremanBuilder fireman = Fireman.builder();

        fireman.id( dto.getId() );
        fireman.name( dto.getName() );
        fireman.surname( dto.getSurname() );
        fireman.idNumber( dto.getIdNumber() );
        fireman.bankAccount( dto.getBankAccount() );

        return fireman.build();
    }

    @Override
    public FireStationDto toFireStationDto(FireStation station) {
        if ( station == null ) {
            return null;
        }

        FireStationDto.FireStationDtoBuilder fireStationDto = FireStationDto.builder();

        fireStationDto.id( station.getId() );
        fireStationDto.name( station.getName() );
        fireStationDto.stationNumber( station.getStationNumber() );
        fireStationDto.phoneNumber( station.getPhoneNumber() );
        fireStationDto.email( station.getEmail() );
        fireStationDto.commanderName( station.getCommanderName() );
        fireStationDto.active( station.getActive() );
        fireStationDto.establishedDate( station.getEstablishedDate() );

        return fireStationDto.build();
    }

    @Override
    public FireStation toFireStation(FireStationDto dto) {
        if ( dto == null ) {
            return null;
        }

        FireStation.FireStationBuilder fireStation = FireStation.builder();

        fireStation.id( dto.getId() );
        fireStation.name( dto.getName() );
        fireStation.stationNumber( dto.getStationNumber() );
        fireStation.phoneNumber( dto.getPhoneNumber() );
        fireStation.email( dto.getEmail() );
        fireStation.commanderName( dto.getCommanderName() );
        fireStation.establishedDate( dto.getEstablishedDate() );
        fireStation.active( dto.getActive() );

        return fireStation.build();
    }

    @Override
    public FireTruckDto toFireTruckDto(FireTruck truck) {
        if ( truck == null ) {
            return null;
        }

        FireTruckDto.FireTruckDtoBuilder fireTruckDto = FireTruckDto.builder();

        fireTruckDto.stationId( fireStationMapper.map( truck.getStation() ) );
        fireTruckDto.id( truck.getId() );
        fireTruckDto.sideNumber( truck.getSideNumber() );
        fireTruckDto.plateNumber( truck.getPlateNumber() );
        fireTruckDto.model( truck.getModel() );
        fireTruckDto.yearOfProduction( truck.getYearOfProduction() );
        fireTruckDto.capacityLiters( truck.getCapacityLiters() );
        fireTruckDto.type( truck.getType() );
        fireTruckDto.typeDescription( truck.getTypeDescription() );
        fireTruckDto.remarks( truck.getRemarks() );

        return fireTruckDto.build();
    }

    @Override
    public FireTruck toFireTruck(FireTruckDto dto) {
        if ( dto == null ) {
            return null;
        }

        FireTruck.FireTruckBuilder fireTruck = FireTruck.builder();

        fireTruck.id( dto.getId() );
        fireTruck.sideNumber( dto.getSideNumber() );
        fireTruck.plateNumber( dto.getPlateNumber() );
        fireTruck.model( dto.getModel() );
        fireTruck.yearOfProduction( dto.getYearOfProduction() );
        fireTruck.capacityLiters( dto.getCapacityLiters() );
        fireTruck.type( dto.getType() );
        fireTruck.typeDescription( dto.getTypeDescription() );
        fireTruck.remarks( dto.getRemarks() );

        return fireTruck.build();
    }

    @Override
    public ReportDto toReportDto(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportDto reportDto = new ReportDto();

        reportDto.setEventId( eventMapper.map( report.getEvent() ) );
        reportDto.setUserId( userMapper.map( report.getUser() ) );
        reportDto.setId( report.getId() );
        if ( report.getType() != null ) {
            reportDto.setType( report.getType().name() );
        }
        byte[] content = report.getContent();
        if ( content != null ) {
            reportDto.setContent( Arrays.copyOf( content, content.length ) );
        }

        return reportDto;
    }

    @Override
    public Report toReport(ReportDto dto) {
        if ( dto == null ) {
            return null;
        }

        Report.ReportBuilder report = Report.builder();

        report.id( dto.getId() );
        if ( dto.getType() != null ) {
            report.type( Enum.valueOf( ReportType.class, dto.getType() ) );
        }
        byte[] content = dto.getContent();
        if ( content != null ) {
            report.content( Arrays.copyOf( content, content.length ) );
        }

        return report.build();
    }

    protected Set<Long> firemanSetToLongSet(Set<Fireman> set) {
        if ( set == null ) {
            return null;
        }

        Set<Long> set1 = new LinkedHashSet<Long>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Fireman fireman : set ) {
            set1.add( firemanMapper.map( fireman ) );
        }

        return set1;
    }
}
