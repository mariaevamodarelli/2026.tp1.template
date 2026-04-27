package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.ValidacionException;
import com.bibliotech.model.Docente;
import com.bibliotech.model.Estudiante;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.SocioRepository;

public class SocioService {

    private final SocioRepository socioRepo;

    public SocioService(SocioRepository socioRepo) {
        this.socioRepo = socioRepo;
    }

    public void registrarEstudiante(int id, String nombre, String email, String dni) throws BibliotecaException {
        validarDatosSocio(email, dni);

        Socio estudiante = new Estudiante(id, nombre, email, dni);
        socioRepo.guardar(estudiante);
    }

    public void registrarDocente(int id, String nombre, String email, String dni) throws BibliotecaException {
        validarDatosSocio(email, dni);

        Socio docente = new Docente(id, nombre, email, dni);
        socioRepo.guardar(docente);
    }

    private void validarDatosSocio(String email, String dni) throws BibliotecaException {
        if (socioRepo.buscarPorDni(dni).isPresent()) {
            throw new ValidacionException("Ya existe un socio con ese DNI");
        }

        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidacionException("El email no tiene un formato válido");
        }
    }
}