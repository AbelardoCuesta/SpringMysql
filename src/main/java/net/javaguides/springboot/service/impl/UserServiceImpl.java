package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        //Conver User Dto into User JPA entity
        User user = UserMapper.mapToUser(userDto);

        User savedUser = userRepository.save(user);

        //Conver user JPA entity to User Dto

        UserDto savedUserDto= UserMapper.mapToUserDto(savedUser);
        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {


        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        //Convert user JPA entity to User Dto
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users = userRepository.findAll();

       return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        User updataedUser = userRepository.save(existingUser);
        return UserMapper.mapToUserDto(updataedUser);

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
