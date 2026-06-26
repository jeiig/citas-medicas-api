package com.mediasalud.booking.application.portsOutput;

import com.mediasalud.booking.domain.model.Penalty;

import java.time.LocalDateTime;

public interface PenaltyRepositoryPort {
    Penalty save(Penalty penalty);

    // Cuenta cuántas penalizaciones tiene el paciente desde una fecha específica (hace 30 días)
    long countByPatientIdAndPenaltyDateTimeAfter(String patientId, LocalDateTime dateTime);
}
