package com.mediasalud.booking.infrastructure.adapters.output.mapper;

import com.mediasalud.booking.domain.model.Penalty;
import com.mediasalud.booking.infrastructure.adapters.output.PenaltyEntity;

public final class PenaltyMapper {

    private PenaltyMapper() {}

    public static PenaltyEntity toEntity(Penalty domain) {
        if (domain == null) return null;
        return new PenaltyEntity(
                domain.getId(),
                domain.getPatientId(),
                domain.getPenaltyDateTime()
        );
    }

    public static Penalty toDomain(PenaltyEntity entity) {
        if (entity == null) return null;
        return new Penalty(
                entity.getId(),
                entity.getPatientId(),
                entity.getPenaltyDateTime()
        );
    }
}
