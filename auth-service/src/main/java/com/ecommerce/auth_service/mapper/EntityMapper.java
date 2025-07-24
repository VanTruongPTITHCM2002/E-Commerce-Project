package com.ecommerce.auth_service.mapper;

/*
    * @param R - request
    * @pram E - entity
    * @param D - response
 */

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface EntityMapper <E, R, D>{

    E toEntity (R r);

    D toResponse (E e);

    @Named("toUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate (@MappingTarget E e, R r);
}
