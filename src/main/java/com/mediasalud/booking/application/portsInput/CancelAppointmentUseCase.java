package com.mediasalud.booking.application.portsInput;

import com.mediasalud.booking.domain.model.Appointment;

public interface CancelAppointmentUseCase {

    Appointment execute(String appointmentId);
}
