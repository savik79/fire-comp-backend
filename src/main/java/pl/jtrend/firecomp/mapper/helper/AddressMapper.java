package pl.jtrend.firecomp.mapper.helper;

import pl.jtrend.firecomp.entity.Address;

@org.mapstruct.Mapper(componentModel =  "spring")
public interface AddressMapper {

    default Long map(Address address) {
        return address == null ? null : address.getId();
    }
}
