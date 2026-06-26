package com.mediasalud.booking.infrastructure.adapters.input.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookAppointmentRequest(@NotBlank(message = "El ID del paciente es obligatorio.")
                                     String patientId,

                                     @NotBlank(message = "El ID del médico es obligatorio.")
                                     String doctorId,

                                     @NotNull(message = "La fecha y hora de la cita es obligatoria.")
                                     LocalDateTime dateTime) {
}
