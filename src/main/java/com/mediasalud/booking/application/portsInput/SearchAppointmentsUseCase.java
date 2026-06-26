package com.mediasalud.booking.application.portsInput;

import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchAppointmentsUseCase {

    List<Appointment> execute(String doctorId, String patientId, AppointmentStatus status, LocalDateTime start, LocalDateTime end);
}
