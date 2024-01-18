package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        //Conver User Dto into User JPA entity
        //User user = UserMapper.mapToUser(userDto);

        //User user = modelMapper.map(userDto, User.class);

        User user = AutoUserMapper.MAPPER.mapToUser(userDto);
        User savedUser = userRepository.save(user);

        //Conver user JPA entity to User Dto

        //UserDto savedUserDto= UserMapper.mapToUserDto(savedUser);
        //UserDto savedUserDto= modelMapper.map(savedUser,UserDto.class);
        UserDto savedUserDto= AutoUserMapper.MAPPER.mapToUserDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {


        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        //User user = optionalUser.get();

        //Convert user JPA entity to User Dto
        //return modelMapper.map(user,UserDto.class);


        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users = userRepository.findAll();

       //return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
       //return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return users.stream().map(user -> AutoUserMapper.MAPPER.mapToUserDto(user)).collect(Collectors.toList());


    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        User updataedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updataedUser);
        //return modelMapper.map(updataedUser,UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(updataedUser);


    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );


        userRepository.deleteById(userId);
    }


}
