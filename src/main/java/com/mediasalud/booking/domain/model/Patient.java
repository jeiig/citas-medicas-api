package com.mediasalud.booking.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class Patient {

    private String id;
    private String name;
    private String documentId;
    private String phone;
    private String email;
    private LocalDate birthDate;

    public Patient(String id, String name, String documentId, String phone, String email, LocalDate birthDate) {
        if (name == null || name.trim().length() < 3 || name.length() > 100) {
            throw new IllegalArgumentException("El nombre del paciente debe tener entre 3 y 100 caracteres.");
        }
        if (documentId == null || documentId.trim().length() < 7) {
            throw new IllegalArgumentException("El documento de identidad debe tener mínimo 7 caracteres.");
        }
        if (phone == null || phone.trim().length() < 7) {
            throw new IllegalArgumentException("El teléfono es obligatorio y debe tener mínimo 7 dígitos.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El formato del email es inválido.");
        }
        this.id = id;
        this.name = name;
        this.documentId = documentId;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate != null ? birthDate : LocalDate.now();
    }

}
