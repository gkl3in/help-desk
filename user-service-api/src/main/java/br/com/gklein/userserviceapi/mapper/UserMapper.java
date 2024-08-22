package br.com.gklein.userserviceapi.mapper;

import br.com.gklein.userserviceapi.entity.User;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {
    UserResponse fromEntity(final User entity);

    @Mapping(target = "id", ignore = true)
    User fromRequest(CreateUserRequest createUserRequest);
}