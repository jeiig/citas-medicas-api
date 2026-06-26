package com.mediasalud.booking.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    private String id;
    private Patient patient;
    private Doctor doctor;
    private AppointmentSlot slot;
    private AppointmentStatus status;
    private LocalDateTime cancellationDateTime;

    public Appointment(String id, Patient patient, Doctor doctor, LocalDateTime dateTime) {
        if (patient == null) {
            throw new IllegalArgumentException("El paciente es obligatorio para agendar la cita.");
        }
        if (doctor == null) {
            throw new IllegalArgumentException("El médico es obligatorio para agendar la cita.");
        }

        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.slot = new AppointmentSlot(dateTime); // Aquí se ejecutan automáticamente las reglas RN-01
        this.status = AppointmentStatus.PROGRAMADA; // RF-03: Inicialmente PROGRAMADA
    }
}
