package com.mediasalud.booking.infrastructure.adapters.output.adapters;


import com.mediasalud.booking.application.portsOutput.PenaltyRepositoryPort;
import com.mediasalud.booking.domain.model.Penalty;
import com.mediasalud.booking.infrastructure.adapters.output.PenaltyEntity;
import com.mediasalud.booking.infrastructure.adapters.output.mapper.PenaltyMapper;
import com.mediasalud.booking.infrastructure.adapters.output.repository.SpringDataPenaltyRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PenaltyRepositoryAdapter implements PenaltyRepositoryPort {

    private final SpringDataPenaltyRepository repository;

    public PenaltyRepositoryAdapter(SpringDataPenaltyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Penalty save(Penalty penalty) {
        PenaltyEntity entity = PenaltyMapper.toEntity(penalty);
        PenaltyEntity savedEntity = repository.save(entity);
        return PenaltyMapper.toDomain(savedEntity);
    }

    @Override
    public long countByPatientIdAndPenaltyDateTimeAfter(String patientId, LocalDateTime dateTime) {
        return repository.countByPatientIdAndPenaltyDateTimeAfter(patientId, dateTime);
    }
}
