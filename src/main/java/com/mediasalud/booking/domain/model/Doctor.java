package com.mediasalud.booking.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Doctor {

    private String id;
    private String name;
    private String specialty;
    private String phone;
    private String email;

    public Doctor(String id, String name, String specialty, String phone, String email) {
        if (name == null || name.trim().length() < 3 || name.length() > 100) {
            throw new IllegalArgumentException("El nombre del médico debe tener entre 3 y 100 caracteres.");
        }
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad es obligatoria.");
        }
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
    }

}
