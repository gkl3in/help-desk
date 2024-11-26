package br.com.gklein.userserviceapi.mapper;

import br.com.gklein.userserviceapi.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import models.enums.ProfileEnum;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T06:30:03-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse fromEntity(User entity) {
        if ( entity == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String email = null;
        String password = null;
        Set<ProfileEnum> profiles = null;

        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getName() != null ) {
            name = entity.getName();
        }
        if ( entity.getEmail() != null ) {
            email = entity.getEmail();
        }
        if ( entity.getPassword() != null ) {
            password = entity.getPassword();
        }
        Set<ProfileEnum> set = entity.getProfiles();
        if ( set != null ) {
            profiles = new LinkedHashSet<ProfileEnum>( set );
        }

        UserResponse userResponse = new UserResponse( id, name, email, password, profiles );

        return userResponse;
    }

    @Override
    public User fromRequest(CreateUserRequest createUserRequest) {
        if ( createUserRequest == null ) {
            return null;
        }

        User user = new User();

        if ( createUserRequest.name() != null ) {
            user.setName( createUserRequest.name() );
        }
        if ( createUserRequest.email() != null ) {
            user.setEmail( createUserRequest.email() );
        }
        if ( createUserRequest.password() != null ) {
            user.setPassword( createUserRequest.password() );
        }
        Set<ProfileEnum> set = createUserRequest.profiles();
        if ( set != null ) {
            user.setProfiles( new LinkedHashSet<ProfileEnum>( set ) );
        }

        return user;
    }

    @Override
    public User update(UpdateUserRequest updateUserRequest, User entity) {
        if ( updateUserRequest == null ) {
            return entity;
        }

        if ( updateUserRequest.name() != null ) {
            entity.setName( updateUserRequest.name() );
        }
        if ( updateUserRequest.email() != null ) {
            entity.setEmail( updateUserRequest.email() );
        }
        if ( updateUserRequest.password() != null ) {
            entity.setPassword( updateUserRequest.password() );
        }
        if ( entity.getProfiles() != null ) {
            Set<ProfileEnum> set = updateUserRequest.profiles();
            if ( set != null ) {
                entity.getProfiles().clear();
                entity.getProfiles().addAll( set );
            }
        }
        else {
            Set<ProfileEnum> set = updateUserRequest.profiles();
            if ( set != null ) {
                entity.setProfiles( new LinkedHashSet<ProfileEnum>( set ) );
            }
        }

        return entity;
    }
}
