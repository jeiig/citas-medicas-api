package com.mediasalud.booking.infrastructure.adapters.output.mapper;

import com.mediasalud.booking.domain.model.Patient;
import com.mediasalud.booking.infrastructure.adapters.output.PatientEntity;

public final class PatientMapper {

    private PatientMapper() {}

    public static PatientEntity toEntity(Patient domain) {
        if (domain == null) return null;
        return new PatientEntity(
                domain.getId(),
                domain.getName(),
                domain.getDocumentId(),
                domain.getPhone(),
                domain.getEmail(),
                domain.getBirthDate()
        );
    }

    public static Patient toDomain(PatientEntity entity) {
        if (entity == null) return null;
        return new Patient(
                entity.getId(),
                entity.getName(),
                entity.getDocumentId(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getBirthDate()
        );
    }
}
