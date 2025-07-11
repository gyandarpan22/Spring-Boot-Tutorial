package com.gyan.darpan.employee.mapper;

import java.util.function.Function;

public interface EntityDTOMapper<E, D> {
    Function<E, D> toDTO();

    Function<D, E> toEntity();
}
