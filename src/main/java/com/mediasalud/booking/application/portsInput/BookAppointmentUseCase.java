package com.mediasalud.booking.application.portsInput;

import com.mediasalud.booking.domain.model.Appointment;

import java.time.LocalDateTime;

public interface BookAppointmentUseCase {

    Appointment execute(String patientId, String doctorId, LocalDateTime dateTime);
}
