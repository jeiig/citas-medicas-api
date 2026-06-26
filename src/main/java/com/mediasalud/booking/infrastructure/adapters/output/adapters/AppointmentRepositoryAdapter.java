package com.mediasalud.booking.infrastructure.adapters.output.adapters;


import com.mediasalud.booking.application.portsOutput.AppointmentRepositoryPort;
import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import com.mediasalud.booking.infrastructure.adapters.output.AppointmentEntity;
import com.mediasalud.booking.infrastructure.adapters.output.mapper.AppointmentMapper;
import com.mediasalud.booking.infrastructure.adapters.output.repository.SpringDataAppointmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private final SpringDataAppointmentRepository repository;

    public AppointmentRepositoryAdapter(SpringDataAppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = AppointmentMapper.toEntity(appointment);
        AppointmentEntity savedEntity = repository.save(entity);
        return AppointmentMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Appointment> findById(String id) {
        return repository.findById(id).map(AppointmentMapper::toDomain);
    }

    @Override
    public boolean existsByDoctorIdAndSlotDateTimeAndStatus(String doctorId, LocalDateTime dateTime, AppointmentStatus status) {
        return repository.existsByDoctorIdAndSlotDateTimeAndStatus(doctorId, dateTime, status.name());
    }

    @Override
    public boolean existsByPatientIdAndDoctorIdAndSlotDateTimeAndStatus(String patientId, String doctorId, LocalDateTime dateTime, AppointmentStatus status) {
        return repository.existsByPatientIdAndDoctorIdAndSlotDateTimeAndStatus(patientId, doctorId, dateTime, status.name());
    }

    @Override
    public List<Appointment> findAppointmentsByFilters(String doctorId, String patientId, AppointmentStatus status, LocalDateTime start, LocalDateTime end) {
        String statusStr = (status != null) ? status.name() : null;
        List<AppointmentEntity> entities = repository.findByFilters(doctorId, patientId, statusStr, start, end);
        return entities.stream()
                .map(AppointmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}
