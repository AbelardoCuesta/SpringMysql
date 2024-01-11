package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        //Conver User Dto into User JPA entity
        User user = new User(userDto.getId(),userDto.getFirstName(),userDto.getLastName(),userDto.getEmail());

        User savedUser = userRepository.save(user);

        //Conver user JPA entity to User Dto

        UserDto savedUserDto= new UserDto(savedUser.getId(),savedUser.getFirstName(),savedUser.getLastName(),savedUser.getEmail());
        return savedUserDto;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        User updataedUser = userRepository.save(existingUser);
        return updataedUser;

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
