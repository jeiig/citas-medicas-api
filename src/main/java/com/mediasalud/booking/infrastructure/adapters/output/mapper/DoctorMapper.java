package com.mediasalud.booking.infrastructure.adapters.output.mapper;

import com.mediasalud.booking.domain.model.Doctor;
import com.mediasalud.booking.infrastructure.adapters.output.DoctorEntity;

public final class DoctorMapper {

    private DoctorMapper() {}

    public static DoctorEntity toEntity(Doctor domain) {
        if (domain == null) return null;
        return new DoctorEntity(
                domain.getId(),
                domain.getName(),
                domain.getSpecialty(),
                domain.getPhone(),
                domain.getEmail()
        );
    }

    public static Doctor toDomain(DoctorEntity entity) {
        if (entity == null) return null;
        return new Doctor(
                entity.getId(),
                entity.getName(),
                entity.getSpecialty(),
                entity.getPhone(),
                entity.getEmail()
        );
    }
}
