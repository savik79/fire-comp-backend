package pl.jtrend.firecomp.mapper.helper;

import pl.jtrend.firecomp.entity.User;

@org.mapstruct.Mapper(componentModel =  "spring")
public interface UserMapper {

    default Long map(User user) {
        return user == null ? null : user.getId();
    }
}
