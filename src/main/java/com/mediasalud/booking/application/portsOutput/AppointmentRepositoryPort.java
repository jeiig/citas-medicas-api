package com.mediasalud.booking.application.portsOutput;

import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {

    Appointment save(Appointment appointment);
    Optional<Appointment> findById(String id);

    // Para RN-02: Verificar si el médico ya tiene una cita programada en esa franja
    boolean existsByDoctorIdAndSlotDateTimeAndStatus(String doctorId, LocalDateTime dateTime, AppointmentStatus status);

    // Para RN-04: Verificar si el paciente ya tiene una cita programada con ESE mismo médico en esa franja
    boolean existsByPatientIdAndDoctorIdAndSlotDateTimeAndStatus(String patientId, String doctorId, LocalDateTime dateTime, AppointmentStatus status);

    // Para RF-04 y RF-06: Buscar por rango de fechas y filtros
    List<Appointment> findAppointmentsByFilters(String doctorId, String patientId, AppointmentStatus status, LocalDateTime start, LocalDateTime end);
}
