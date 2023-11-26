package com.project.auth.mapper;


import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.model.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    User toUser(RegistrationRequest registrationRequest);
}
