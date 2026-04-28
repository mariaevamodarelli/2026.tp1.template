package com.bibliotech.model;

import java.time.LocalDate;
import java.util.Optional;

public class Prestamo {

    private final int id;
    private final Recurso recurso;  // Cambiado de Libro a Recurso para soportar LibroFisico y Ebook
    private final Socio socio;
    private final LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(int id, Recurso recurso, Socio socio, LocalDate fechaPrestamo) {
        this.id = id;
        this.recurso = recurso;
        this.socio = socio;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
    }

    public int getId() {
        return id;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public Socio getSocio() {
        return socio;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Optional<LocalDate> getFechaDevolucion() {
        return Optional.ofNullable(fechaDevolucion);
    }

    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public void registrarDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}