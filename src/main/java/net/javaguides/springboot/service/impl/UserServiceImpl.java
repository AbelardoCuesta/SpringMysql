package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
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

        User user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

        //Conver user JPA entity to User Dto

        //UserDto savedUserDto= UserMapper.mapToUserDto(savedUser);
        UserDto savedUserDto= modelMapper.map(savedUser,UserDto.class);
        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {


        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        //Convert user JPA entity to User Dto
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users = userRepository.findAll();

       //return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
       return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        User updataedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updataedUser);
        return modelMapper.map(updataedUser,UserDto.class);

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
