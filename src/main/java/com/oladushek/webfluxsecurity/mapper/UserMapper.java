package com.oladushek.webfluxsecurity.mapper;

import com.oladushek.webfluxsecurity.web.dto.UserDto;
import com.oladushek.webfluxsecurity.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map (UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map (UserDto userDto);
}
