package com.mediasalud.booking.infrastructure.adapters.output.adapters;


import com.mediasalud.booking.application.portsOutput.PatientRepositoryPort;
import com.mediasalud.booking.domain.model.Patient;
import com.mediasalud.booking.infrastructure.adapters.output.PatientEntity;
import com.mediasalud.booking.infrastructure.adapters.output.mapper.PatientMapper;
import com.mediasalud.booking.infrastructure.adapters.output.repository.SpringDataPatientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientRepositoryAdapter implements PatientRepositoryPort {

    private final SpringDataPatientRepository repository;

    public PatientRepositoryAdapter(SpringDataPatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = PatientMapper.toEntity(patient);
        PatientEntity savedEntity = repository.save(entity);
        return PatientMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Patient> findById(String id) {
        return repository.findById(id).map(PatientMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByDocumentId(String documentId) {
        return repository.findByDocumentId(documentId).map(PatientMapper::toDomain);
    }
}
