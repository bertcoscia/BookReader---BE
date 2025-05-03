package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.UserRequest;
import com.bertcoscia.BookReader.entities.User;
import com.bertcoscia.BookReader.exceptions.BadRequestException;
import com.bertcoscia.BookReader.exceptions.NotFoundException;
import com.bertcoscia.BookReader.mappers.UserMapper;
import com.bertcoscia.BookReader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User save(UserRequest request) {
        if (this.userRepository.existsByEmail(request.email()))
            throw new BadRequestException("Email already used");

        if (this.userRepository.existsByUsername(request.username()))
            throw new BadRequestException("Username already used");

        return this.userRepository.save(this.userMapper.toEntity(request));
    }

    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Could not find user with email " + email));
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User update(Long id, User userUpdated) {
        User userToUpdate = this.findById(id);

        if (this.userRepository.existsByEmail(userUpdated.getEmail()))
            throw new BadRequestException("Email already used");

        if (this.userRepository.existsByUsername(userUpdated.getUsername()))
            throw new BadRequestException("Username already used");

        return this.userMapper.update(userToUpdate, userUpdated);
    }

    public void delete(Long id) {
        this.userRepository.delete(this.findById(id));
    }


}
