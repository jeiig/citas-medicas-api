package com.mediasalud.booking.infrastructure.adapters.input.dto;

import com.mediasalud.booking.domain.model.Appointment;

import java.time.LocalDateTime;

public record AppointmentResponse(String id,
                                  String patientId,
                                  String patientName,
                                  String doctorId,
                                  String doctorName,
                                  String specialty,
                                  LocalDateTime dateTime,
                                  String status
) {
    // ESTE ES EL MeTODO QUE LE FALTA A TU ARCHIVO O QUE QUEDÓ MAL UBICADO:
    public static AppointmentResponse fromDomain(Appointment domain) {
        return new AppointmentResponse(
                domain.getId(),
                domain.getPatient().getId(),
                domain.getPatient().getName(),
                domain.getDoctor().getId(),
                domain.getDoctor().getName(),
                domain.getDoctor().getSpecialty(),
                domain.getSlot().dateTime(),
                domain.getStatus().name()
        );
    }
}