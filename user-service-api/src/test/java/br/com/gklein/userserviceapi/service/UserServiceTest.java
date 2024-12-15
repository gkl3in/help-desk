package br.com.gklein.userserviceapi.service;

import br.com.gklein.userserviceapi.entity.User;
import br.com.gklein.userserviceapi.mapper.UserMapper;
import br.com.gklein.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.gklein.userserviceapi.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private static final String VALID_USER_ID = "1";
    private static final String MOCK_ENCODED_PASSWORD = "encoded";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void shouldReturnUserResponseWhenFindByValidId() {
        User mockUser = new User();
        UserResponse expectedResponse = generateMock(UserResponse.class);

        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(mockUser));
        when(userMapper.fromEntity(mockUser)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.findById(VALID_USER_ID);

        assertNotNull(actualResponse);
        assertEquals(UserResponse.class, actualResponse.getClass());
        verify(userRepository).findById(VALID_USER_ID);
        verify(userMapper).fromEntity(mockUser);
    }

    @Test
    void shouldThrowExceptionWhenFindByInvalidId() {
        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findById(VALID_USER_ID)
        );

        assertEquals("Object not found. Id: 1, Type: UserResponse", exception.getMessage());
        verify(userRepository).findById(VALID_USER_ID);
        verify(userMapper, never()).fromEntity(any());
    }

    @Test
    void shouldReturnAllUsersWhenFindAll() {
        List<User> mockUsers = List.of(new User(), new User());
        UserResponse mockResponse = mock(UserResponse.class);

        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userMapper.fromEntity(any(User.class))).thenReturn(mockResponse);

        List<UserResponse> responses = userService.findAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(UserResponse.class, responses.get(0).getClass());
        verify(userRepository).findAll();
        verify(userMapper, times(2)).fromEntity(any());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserRequest createRequest = generateMock(CreateUserRequest.class);
        User newUser = new User();

        when(userMapper.fromRequest(any())).thenReturn(newUser);
        when(passwordEncoder.encode(anyString())).thenReturn(MOCK_ENCODED_PASSWORD);
        when(userRepository.save(any())).thenReturn(newUser);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.save(createRequest);

        verify(userMapper).fromRequest(createRequest);
        verify(passwordEncoder).encode(createRequest.password());
        verify(userRepository).save(any());
        verify(userRepository).findByEmail(createRequest.email());
    }

    @Test
    void shouldThrowExceptionWhenCreateUserWithExistingEmail() {
        CreateUserRequest createRequest = generateMock(CreateUserRequest.class);
        User existingUser = generateMock(User.class);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        DataIntegrityViolationException exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.save(createRequest)
        );

        assertEquals("Email [ " + createRequest.email() + " ] already exists", exception.getMessage());
        verifyNoUserCreationCalls(createRequest);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UpdateUserRequest updateRequest = generateMock(UpdateUserRequest.class);
        User existingUser = generateMock(User.class).withId(VALID_USER_ID);

        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));
        when(userMapper.update(any(), any())).thenReturn(existingUser);
        when(userRepository.save(any())).thenReturn(existingUser);

        userService.update(VALID_USER_ID, updateRequest);

        verifyUpdateOperationCalls(updateRequest, existingUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdateNonexistentUser() {
        UpdateUserRequest updateRequest = generateMock(UpdateUserRequest.class);
        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.update(VALID_USER_ID, updateRequest)
        );

        assertEquals("Object not found. Id: 1, Type: UserResponse", exception.getMessage());
        verifyNoUpdateCalls(updateRequest);
    }

    @Test
    void shouldThrowExceptionWhenUpdateWithDuplicateEmail() {
        UpdateUserRequest updateRequest = generateMock(UpdateUserRequest.class);
        User existingUser = generateMock(User.class);

        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        DataIntegrityViolationException exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.update(VALID_USER_ID, updateRequest)
        );

        assertEquals("Email [ " + updateRequest.email() + " ] already exists", exception.getMessage());
        verifyNoDuplicateEmailUpdateCalls(updateRequest);
    }

    private void verifyNoUserCreationCalls(CreateUserRequest request) {
        verify(userRepository).findByEmail(request.email());
        verify(userMapper, never()).fromRequest(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    private void verifyUpdateOperationCalls(UpdateUserRequest request, User entity) {
        verify(userRepository).findById(VALID_USER_ID);
        verify(userRepository).findByEmail(request.email());
        verify(userMapper).update(request, entity);
        verify(passwordEncoder).encode(request.password());
        verify(userRepository).save(any());
        verify(userMapper).fromEntity(any());
    }

    private void verifyNoUpdateCalls(UpdateUserRequest request) {
        verify(userRepository).findById(VALID_USER_ID);
        verify(userMapper, never()).update(any(), any());
        verify(passwordEncoder, never()).encode(request.password());
        verify(userRepository, never()).save(any());
    }

    private void verifyNoDuplicateEmailUpdateCalls(UpdateUserRequest request) {
        verify(userRepository).findById(VALID_USER_ID);
        verify(userRepository).findByEmail(request.email());
        verify(userMapper, never()).update(any(), any());
        verify(passwordEncoder, never()).encode(request.password());
        verify(userRepository, never()).save(any());
    }
}