package com.mediasalud.booking.domain.model;

import com.mediasalud.booking.domain.exception.BusinessException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentSlot(LocalDateTime dateTime) {

    public AppointmentSlot {
        if (dateTime == null) {
            throw new BusinessException("La fecha y hora de la cita es obligatoria.");
        }

        // RN-01: Bloques exactos de 30 minutos
        if (dateTime.getMinute() != 0 && dateTime.getMinute() != 30) {
            throw new BusinessException("Las citas deben programarse en franjas exactas de 30 minutos.");
        }

        if (dateTime.getSecond() != 0 || dateTime.getNano() != 0) {
            throw new BusinessException("La franja horaria no debe incluir segundos ni milisegundos.");
        }

        DayOfWeek day = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();

        // RN-01: No hay atención los domingos
        if (day == DayOfWeek.SUNDAY) {
            throw new BusinessException("No hay atención los domingos.");
        }

        // RN-01: Sábados de 08:00 a 13:00 (última cita empieza 12:30)
        if (day == DayOfWeek.SATURDAY) {
            if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(12, 30))) {
                throw new BusinessException("El horario de atención para los sábados es de 08:00 a 13:00.");
            }
        } else {
            // RN-01: Lunes a Viernes de 08:00 a 18:00 (última cita empieza 17:30)
            if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(17, 30))) {
                throw new BusinessException("El horario de atención de lunes a viernes es de 08:00 a 18:00.");
            }
        }
    }
}
