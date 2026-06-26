package com.mediasalud.booking.infrastructure.adapters.output.mapper;

import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import com.mediasalud.booking.infrastructure.adapters.output.AppointmentEntity;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;
        return new AppointmentEntity(
                domain.getId(),
                PatientMapper.toEntity(domain.getPatient()),
                DoctorMapper.toEntity(domain.getDoctor()),
                domain.getSlot().dateTime(),
                domain.getStatus().name(),
                domain.getCancellationDateTime()
        );
    }

    public static Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;
        Appointment domain = new Appointment(
                entity.getId(),
                PatientMapper.toDomain(entity.getPatient()),
                DoctorMapper.toDomain(entity.getDoctor()),
                entity.getSlotDateTime()
        );
        domain.setStatus(AppointmentStatus.valueOf(entity.getStatus()));
        domain.setCancellationDateTime(entity.getCancellationDateTime());
        return domain;
    }
}
