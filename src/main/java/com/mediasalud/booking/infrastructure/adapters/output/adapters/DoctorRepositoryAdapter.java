package com.mediasalud.booking.infrastructure.adapters.output.adapters;


import com.mediasalud.booking.application.portsOutput.DoctorRepositoryPort;
import com.mediasalud.booking.domain.model.Doctor;
import com.mediasalud.booking.infrastructure.adapters.output.DoctorEntity;
import com.mediasalud.booking.infrastructure.adapters.output.mapper.DoctorMapper;
import com.mediasalud.booking.infrastructure.adapters.output.repository.SpringDataDoctorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DoctorRepositoryAdapter implements DoctorRepositoryPort {

    private final SpringDataDoctorRepository repository;

    public DoctorRepositoryAdapter(SpringDataDoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Doctor save(Doctor doctor) {
        DoctorEntity entity = DoctorMapper.toEntity(doctor);
        DoctorEntity savedEntity = repository.save(entity);
        return DoctorMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Doctor> findById(String id) {
        return repository.findById(id).map(DoctorMapper::toDomain);
    }
}
