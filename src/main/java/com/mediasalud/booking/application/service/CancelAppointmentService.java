package com.mediasalud.booking.application.service;


import com.mediasalud.booking.application.portsInput.CancelAppointmentUseCase;
import com.mediasalud.booking.application.portsOutput.AppointmentRepositoryPort;
import com.mediasalud.booking.application.portsOutput.PenaltyRepositoryPort;
import com.mediasalud.booking.domain.exception.BusinessException;
import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import com.mediasalud.booking.domain.model.Penalty;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CancelAppointmentService implements CancelAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final PenaltyRepositoryPort penaltyRepository;

    public CancelAppointmentService(AppointmentRepositoryPort appointmentRepository,
                                    PenaltyRepositoryPort penaltyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    public Appointment execute(String appointmentId) {
        // 1. Validar que la cita exista
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new BusinessException("La cita especificada no existe."));

        // 2. Validar que la cita no haya sido cancelada o atendida previamente
        if (appointment.getStatus() == AppointmentStatus.CANCELADA) {
            throw new BusinessException("La cita ya se encuentra cancelada.");
        }
        if (appointment.getStatus() == AppointmentStatus.ATENDIDA) {
            throw new BusinessException("No se puede cancelar una cita que ya fue atendida.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentDateTime = appointment.getSlot().dateTime();

        // 3. RN-05: Validar si la cancelación es tardía (menos de 2 horas de anticipación)
        // Si la cita ya pasó o faltan menos de 120 minutos, aplica penalización
        long minutesToAppointment = Duration.between(now, appointmentDateTime).toMinutes();

        if (minutesToAppointment < 120) {
            Penalty penalty = new Penalty(
                    UUID.randomUUID().toString(),
                    appointment.getPatient().getId(),
                    now
            );
            penaltyRepository.save(penalty);
        }

        // 4. Actualizar el estado de la cita
        appointment.setStatus(AppointmentStatus.CANCELADA);
        appointment.setCancellationDateTime(now);

        return appointmentRepository.save(appointment);
    }
}
