package pl.jtrend.firecomp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jtrend.firecomp.dto.UserDto;
import pl.jtrend.firecomp.entity.User;
import pl.jtrend.firecomp.mapper.FireCompMapper;
import pl.jtrend.firecomp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FireCompMapper mapper;

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(mapper::toUserDto).toList();
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id).map(mapper::toUserDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto create(UserDto dto) {
        return mapper.toUserDto(userRepository.save(mapper.toUser(dto)));
    }

    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(dto.getUsername());
        user.setEnabled(dto.isEnabled());
        user.setRoles(dto.getRoles());
        return mapper.toUserDto(userRepository.save(user));
    }
}
