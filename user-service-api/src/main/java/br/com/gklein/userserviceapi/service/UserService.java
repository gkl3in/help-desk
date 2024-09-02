package br.com.gklein.userserviceapi.service;

import br.com.gklein.userserviceapi.mapper.UserMapper;
import br.com.gklein.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse findById(final String id) {
        return mapper.fromEntity(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Object not found. Id: " + id + " type: " + UserResponse.class.getSimpleName()
                        ))
        );
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailAlreadyExists(request.email(), null);
        repository.save(
                mapper.fromRequest(request));
    }

    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        repository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
                });
    }
}