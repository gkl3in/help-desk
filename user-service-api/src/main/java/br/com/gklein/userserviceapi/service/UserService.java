package br.com.gklein.userserviceapi.service;

import br.com.gklein.userserviceapi.mapper.UserMapper;
import br.com.gklein.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Object not found. Id: " + id + " type: " + UserResponse.class.getSimpleName()
                        ))
        );
    }
}